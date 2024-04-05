package searchengine.services.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import searchengine.config.SitesList;
import searchengine.dto.site.SiteDto;
import searchengine.dto.statistics.DetailedStatisticsItem;
import searchengine.dto.statistics.StatisticsData;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.dto.statistics.TotalStatistics;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final SitesList sites;
    private final PageRepository pageRepository;
private final SiteRepository siteRepository;
    @Override
    public StatisticsResponse getStatistics() {



        TotalStatistics total = new TotalStatistics();
        total.setSites(sites.getSites().size());
        total.setPages((int) pageRepository.count());
        total.setLemmas(0);


        total.setIndexing(true);

        List<DetailedStatisticsItem> detailed = new ArrayList<>();
        List<SiteDto> sitesList = sites.getSites();
        sitesList.forEach(site -> {
            DetailedStatisticsItem item = new DetailedStatisticsItem();
            item.setName(site.getName());
            item.setUrl(site.getUrl());
            item.setPages(pageRepository.countBySiteId(siteRepository.findByUrl(site.getUrl()).getId()));
            item.setLemmas(0);
            item.setStatus(siteRepository.findByUrl(site.getUrl()).getStatus().toString());
            item.setError(site.getLastError());
            item.setStatusTime(System.currentTimeMillis());
            detailed.add(item);
        });
        StatisticsResponse response = new StatisticsResponse();
        StatisticsData data = new StatisticsData();
        data.setTotal(total);
        data.setDetailed(detailed);
        response.setStatistics(data);
        response.setResult(true);
        return response;
    }
}
