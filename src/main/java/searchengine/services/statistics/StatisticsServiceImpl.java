package searchengine.services.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import searchengine.dto.statistics.DetailedStatisticsItem;
import searchengine.dto.statistics.StatisticsData;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.dto.statistics.TotalStatistics;
import searchengine.entity.Status;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.services.datebase.DateBaseService;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {


    private final DateBaseService dateBaseService;


    @Override
    public StatisticsResponse getStatistics() {


        TotalStatistics total = new TotalStatistics(dateBaseService.countSite(), dateBaseService.countPage(),
                dateBaseService.countPage(), true);

        List<DetailedStatisticsItem> detailedList = new ArrayList<>();
        dateBaseService.getAllSites().forEach(site -> {
            DetailedStatisticsItem detailed = new DetailedStatisticsItem();
            detailed.setUrl(site.getUrl());
            detailed.setName(site.getName());
            detailed.setStatus(site.getStatus());
            detailed.setStatusTime(site.getStatusTime());
            detailed.setError(site.getLastError());
            detailed.setPages(dateBaseService.countPageBySiteId(site.getId()));
            detailed.setLemmas(dateBaseService.countPageBySiteId(site.getId()));
            detailedList.add(detailed);
        });
        StatisticsData statisticsOut = new StatisticsData(total, detailedList) {

        };

        return new StatisticsResponse(true, statisticsOut);
    }
}
