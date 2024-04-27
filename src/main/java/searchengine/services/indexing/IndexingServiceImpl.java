package searchengine.services.indexing;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import searchengine.config.ConnectionConfig;
import searchengine.config.StartAndStop;
import searchengine.config.SitesList;
import searchengine.dto.ResponseDto;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Status;

import searchengine.services.datebase.DateBaseService;
import searchengine.utils.CreateSitesMap;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class IndexingServiceImpl implements IndexingService {
    private final SitesList sites;
    private final DateBaseService dateBaseService;
    private final ConnectionConfig connectionConfig;

    @Override
    public ResponseDto startIndexing() {
        if (StartAndStop.getStart()) {
            return new ResponseDto(false, "Indexing is already running");
        }
        StartAndStop.setStart(true);
        dateBaseService.deleterAll();
        List<SiteDto> sitesList = sites.getSites();
        for (SiteDto site : sitesList) {
            CompletableFuture.runAsync(() -> createThreadForIndexingSite(site));
        }
        return new ResponseDto(true);
    }

    @Async
    public void createThreadForIndexingSite(SiteDto siteDto) {
        siteDto = dateBaseService.createSite(siteDto, Status.INDEXING, "There are no errors");
        CopyOnWriteArraySet<String> sitesMap = new CopyOnWriteArraySet<>();
        ForkJoinPool pool = new ForkJoinPool();
        CreateSitesMap createSitesMap = new CreateSitesMap(siteDto, sitesMap, siteDto.getUrl(), dateBaseService, connectionConfig);
        pool.invoke(createSitesMap);
        finishedIndexing(siteDto);
    }

    public void finishedIndexing(SiteDto siteDto) {
        if (StartAndStop.getStart()) {
            log.info("?????????? ????? " + siteDto.getUrl() + "  ????????? ??? ??????");
            dateBaseService.updateStatusSite(siteDto, Status.INDEXED);
            dateBaseService.updateErrorSite(siteDto, "?????????? ????????? ??? ??????");

        } else {
            log.info("?????????? ????? " + siteDto.getUrl() + " ???????????");
            dateBaseService.updateStatusSite(siteDto, Status.FAILED);
            dateBaseService.updateErrorSite(siteDto, "Indexing stop");
        }
        if (dateBaseService.countSitesByStatus(Status.INDEXING) == 0) {
            StartAndStop.setStart(false);
        }
    }

    @Override
    public ResponseDto stopIndexing() {
        if (!StartAndStop.getStart()) {
            return new ResponseDto(false, "Indexing is not running");
        }
        StartAndStop.setStart(false);
        return new ResponseDto(true);
    }

    @Override
    public ResponseDto indexPage(String url) throws MalformedURLException {
        String urlReplace = url.toLowerCase().replace("url=", "").replace("%3A", ":").replace("%2F", "/");
        if(StartAndStop.getStart()){
            return new ResponseDto(false, "Indexing is running");
        }
        if (!sites.getSites().stream().anyMatch(site -> urlReplace.contains(site.getUrl()))) {
            return new ResponseDto(false, "This page is located outside the sites\n" +
                    "specified in the configuration file");
        }
        dateBaseService.deletePage(urlReplace);
        CreateSitesMap createSitesMap = new CreateSitesMap();
        createSitesMap.indexPage(urlReplace, dateBaseService, connectionConfig);

        return new ResponseDto(true);
    }

}
