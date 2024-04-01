package searchengine.services.indexing;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.criteria.internal.expression.ConcatExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import searchengine.config.IsStart;
import searchengine.config.SiteConfig;
import searchengine.config.SitesList;
import searchengine.dto.ResponseDto;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Site;
import searchengine.mapper.SiteMapper;
import searchengine.repository.SiteRepository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class IndexingService {

    private final  IsStart isStart;
    private final SiteRepository siteRepository;
    private final SitesList sites;

    private final SiteMapper siteMapper;
    private final ConcurrentHashMap<Site, String> sitesMap = new ConcurrentHashMap<>();


    public ResponseDto startIndexing() {
        if (isStart.getStart().get()) {
            return new ResponseDto(false, "Индексация уже запущена");
        }
        isStart.getStart().getAndSet(true);
        siteRepository.deleteAll();
        List<SiteConfig> sitesList = sites.getSites();
        for (SiteConfig site : sitesList) {
            SiteDto siteDto = createSite(site);
            indexing(siteDto);
        }
        return new ResponseDto(true);
    }

    private void indexing(SiteDto siteDto) {
    }

    private SiteDto createSite(SiteConfig site) {
        SiteDto siteDto = new SiteDto();
        siteDto.setName(site.getName());
        siteDto.setUrl(site.getUrl());
        siteRepository.saveAndFlush(siteMapper.siteDtoToSite(siteDto));
        return siteDto;
    }
}
