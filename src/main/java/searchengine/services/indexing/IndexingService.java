package searchengine.services.indexing;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import searchengine.config.IsStart;
import searchengine.config.SitesList;
import searchengine.dto.ResponseDto;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Site;
import searchengine.entity.Status;
import searchengine.mapper.SiteMapper;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.services.datebase.DateBaseService;
import searchengine.utils.ParseUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ForkJoinPool;

@Service
@RequiredArgsConstructor
public class IndexingService {

    private final IsStart isStart;
    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;
    private final SitesList sites;
    private final DateBaseService dateBaseService;
    private final SiteMapper siteMapper;
    private final ConcurrentSkipListSet<String> sitesMap = new ConcurrentSkipListSet<>();


    public ResponseDto startIndexing() {
        if (IsStart.getStart()) {
            return new ResponseDto(false, "Индексация уже запущена");
        }
        IsStart.setStart(true);
        dateBaseService.deleteAll();
        List<SiteDto> sitesList = sites.getSites();

        for (SiteDto site : sitesList) {
            CompletableFuture.runAsync(() -> indexingSites(site));
        }
        return new ResponseDto(true);
    }

    @Async
    public void indexingSites(SiteDto siteDto) {
        SiteDto site = siteCreate(siteDto, Status.INDEXING);
        ForkJoinPool pool = new ForkJoinPool();
        ParseUtil parseUtil = new ParseUtil(site, sitesMap, site.getUrl(), dateBaseService);
        pool.invoke(parseUtil);
        pool.shutdown();

        if(isStart.getStart()){
            siteCreate(site, Status.INDEXED);
        }
        if(pool.isTerminated()){
            IsStart.setStart(false);
        }
    }



    @Transactional
    public SiteDto siteCreate(SiteDto siteDto, Status status) {
        siteDto.setStatus(status);
        SiteDto site = siteMapper.siteToSiteDto(siteRepository.saveAndFlush(siteMapper.siteDtoToSite(siteDto)));
        return site;
    }

    public ResponseDto stopIndexing() {
        if (!IsStart.getStart()) {
            return new ResponseDto(false, "Индексация не запущена");
        }
        IsStart.setStart(false);
        List<Site> sites = siteRepository.findAll();
        for (Site site : sites) {
            if (!site.getStatus().equals(Status.INDEXED)) {
               siteCreate(siteMapper.siteToSiteDto(site), Status.FAILED);
            }
         }
        return new ResponseDto(true);
        }

}
