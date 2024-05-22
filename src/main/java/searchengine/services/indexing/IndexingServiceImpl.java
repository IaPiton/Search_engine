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
import searchengine.entity.Lemma;
import searchengine.entity.Site;
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
        Site site = dateBaseService.createSite(siteDto, Status.INDEXING, "Ошибок нет");
        CopyOnWriteArraySet<String> sitesMap = new CopyOnWriteArraySet<>();
        ForkJoinPool pool = new ForkJoinPool();
        CreateSitesMap createSitesMap = new CreateSitesMap(site, sitesMap, site.getUrl(), dateBaseService, connectionConfig);
        pool.invoke(createSitesMap);
        finishedIndexing(site);
    }

    public void finishedIndexing(Site site) {
        if (StartAndStop.getStart()) {
            log.info("Индексация сайта " + site.getUrl() + "  окончена без ошибок");
            dateBaseService.updateStatusAndErrorSite(site, Status.INDEXED, "При индексации сайта ошибок не произошло");
        } else {
            log.info("Индексация сайта " + site.getUrl() + " остановлена");
            dateBaseService.updateStatusAndErrorSite(site, Status.FAILED, "Индексация остановлена");
        }
        if (dateBaseService.countSitesByStatus(Status.INDEXING) == 0) {
            StartAndStop.setStart(false);
        }
    }

    @Override
    public ResponseDto stopIndexing() {
        if (!StartAndStop.getStart()) {
            return new ResponseDto(false, "Индексация не выполняется");
        }
        StartAndStop.setStart(false);
        return new ResponseDto(true);
    }

    @Override
    public ResponseDto indexPage(String url)  {
        String urlReplace = url.toLowerCase().replace("url=", "").replace("%3a", ":").replace("%2f", "/");
        if (StartAndStop.getStart()) {
            return new ResponseDto(false, "Выполняется индексация");
        }
        if (sites.getSites().stream().noneMatch(site -> urlReplace.contains(site.getUrl()))) {
            return new ResponseDto(false, "Эта страница находится за пределами сайтов\n" +
                    ", указанных в файле конфигурации");
        }
        deletePage(urlReplace);
        CreateSitesMap createSitesMap = new CreateSitesMap();
        createSitesMap.indexPage(urlReplace, dateBaseService, connectionConfig);
        return new ResponseDto(true);
    }

    private void deletePage(String urlReplace) {
        List<Lemma> lemmaByPage = dateBaseService.findLemmaByPageId(dateBaseService.findPageByUrl(urlReplace));
        dateBaseService.deleteIndexesByLemma(lemmaByPage);
        dateBaseService.deleteLemmaByPage(lemmaByPage);
        dateBaseService.deletePageByPath(urlReplace);
    }

}
