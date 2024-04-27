package searchengine.dto.statistics;



import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatisticsDataTest {

    @Test
    public void getStatisticsDataTest() {
        StatisticsData statisticsData = new StatisticsData(new TotalStatistics(0L, 0L, 0L, false), new ArrayList<DetailedStatisticsItem>());
        statisticsData.setDetailed(null);
        statisticsData.setTotal(null);
        assertEquals(null, statisticsData.getDetailed());
        assertEquals(null, statisticsData.getTotal());
    }
}
