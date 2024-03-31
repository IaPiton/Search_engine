package searchengine.services.indexing;

import lombok.RequiredArgsConstructor;
import org.hibernate.query.criteria.internal.expression.ConcatExpression;
import org.springframework.stereotype.Service;
import searchengine.config.IsStart;
import searchengine.config.SiteConfig;
import searchengine.config.SitesList;
import searchengine.dto.ResponseDto;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Site;
import searchengine.repository.SiteRepository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class IndexingService {
    private final SitesList sitesList;
    private final IsStart isStart;
    private final SiteRepository siteRepository;
    private final ConcurrentHashMap<Site, String> sitesMap = new ConcurrentHashMap<>();
    private final List<SiteConfig> sites = sitesList.getSites();

    public ResponseDto startIndexing() {
        if (!isStart.getStart()) {
            return new ResponseDto(false, "Индексация уже запущена");
        }
        isStart.setStart(new AtomicBoolean(true));
        siteRepository.deleteAll();
        for (SiteConfig site : sites) {
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
        return siteDto;
    }
}
