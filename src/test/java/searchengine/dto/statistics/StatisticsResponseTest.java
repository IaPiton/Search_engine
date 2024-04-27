package searchengine.dto.statistics;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatisticsResponseTest {

    @Test
    public void getStatisticsResponseTest() {
        StatisticsResponse statisticsResponse = new StatisticsResponse(false, null);
        statisticsResponse.setResult(true);
        statisticsResponse.setStatistics(null);
        assertEquals(true, statisticsResponse.getResult());
        assertEquals(null, statisticsResponse.getStatistics());
    }
}
