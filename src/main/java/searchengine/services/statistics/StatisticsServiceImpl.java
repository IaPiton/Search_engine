package searchengine.services.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import searchengine.dto.statistics.DetailedStatisticsItem;
import searchengine.dto.statistics.StatisticsData;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.dto.statistics.TotalStatistics;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;


import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {


    private final PageRepository pageRepository;
    private final SiteRepository siteRepository;

    @Override
    public StatisticsResponse getStatistics() {


        TotalStatistics total = new TotalStatistics(siteRepository.count(), pageRepository.count(),
                pageRepository.count(), true);

        List<DetailedStatisticsItem> detailedList = new ArrayList<>();
        siteRepository.findAll().forEach(site -> {
            DetailedStatisticsItem detailed = new DetailedStatisticsItem(site.getUrl(), site.getName(), site.getStatus(),
                    site.getStatusTime(), site.getLastError(), pageRepository.countBySiteId(site.getId()),
                    pageRepository.countBySiteId(site.getId()));
            detailedList.add(detailed);
        });
        StatisticsData statisticsOut = new StatisticsData(total, detailedList) {

        };

        return new StatisticsResponse(true, statisticsOut);
    }
}
