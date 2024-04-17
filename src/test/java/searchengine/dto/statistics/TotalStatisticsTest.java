package searchengine.dto.statistics;

import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TotalStatisticsTest {

    @Test
    public void getTotalStatisticsTest() {
        TotalStatistics totalStatistics = new TotalStatistics(0L, 0L, 0L, false);
        totalStatistics.setIndexing(false);
        totalStatistics.setLemmas(0L);
        totalStatistics.setPages(0L);
        totalStatistics.setSites(0L);
        assertEquals(0L, totalStatistics.getSites());
        assertEquals(0L, totalStatistics.getPages());
        assertEquals(0L, totalStatistics.getLemmas());
        assertEquals(false, totalStatistics.isIndexing());
    }
}
