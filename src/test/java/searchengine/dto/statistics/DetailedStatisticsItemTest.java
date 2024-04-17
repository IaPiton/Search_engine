package searchengine.dto.statistics;

import org.testng.annotations.Test;
import searchengine.entity.Status;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetailedStatisticsItemTest {

    @Test
    public void getDetailedStatisticsItemTest() {
        DetailedStatisticsItem detailedStatisticsItem = new DetailedStatisticsItem("url", "name", Status.INDEXING, LocalDateTime.of(2020, 1, 1, 0, 0), "error", 1, 0);
        assertEquals("url", detailedStatisticsItem.getUrl());
        assertEquals("name", detailedStatisticsItem.getName());
        assertEquals(Status.INDEXING, detailedStatisticsItem.getStatus());
        assertEquals(LocalDateTime.of(2020, 1, 1, 0, 0), detailedStatisticsItem.getStatusTime());
        assertEquals("error", detailedStatisticsItem.getError());
        assertEquals(1, detailedStatisticsItem.getPages());
        assertEquals(0, detailedStatisticsItem.getLemmas());
    }
}
