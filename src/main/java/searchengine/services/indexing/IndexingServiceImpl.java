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
            return new ResponseDto(true, "Индексация уже запущена");
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
        siteDto = dateBaseService.createSite(siteDto, Status.INDEXING, "Ошибок нет");
        CopyOnWriteArraySet<String> sitesMap = new CopyOnWriteArraySet<>();
        ForkJoinPool pool = new ForkJoinPool();
        CreateSitesMap createSitesMap = new CreateSitesMap(siteDto, sitesMap, siteDto.getUrl(), dateBaseService, connectionConfig);
        pool.invoke(createSitesMap);
        finishedIndexing(siteDto);
    }

    public void finishedIndexing(SiteDto siteDto) {
        if (StartAndStop.getStart()) {
            log.info("Индексация сайта " + siteDto.getUrl() + "  закончена без ошибок");
            dateBaseService.updateStatusSite(siteDto, Status.INDEXED);
            dateBaseService.updateErrorSite(siteDto, "Индексация закончена без ошибок");

        } else {
            log.info("Индексация сайта " + siteDto.getUrl() + " остановлена");
            dateBaseService.updateStatusSite(siteDto, Status.FAILED);
            dateBaseService.updateErrorSite(siteDto, "Индексация остановлена");
        }
        if (dateBaseService.countSitesByStatus(Status.INDEXING) == 0) {
            StartAndStop.setStart(false);
        }
    }

    @Override
    public ResponseDto stopIndexing() {
        if (!StartAndStop.getStart()) {
            return new ResponseDto(false, "Индексация не запущена");
        }
        StartAndStop.setStart(false);
        return new ResponseDto(true);
    }

}
