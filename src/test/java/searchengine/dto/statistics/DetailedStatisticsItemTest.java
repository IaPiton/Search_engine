package searchengine.dto.statistics;


import org.junit.jupiter.api.Test;
import searchengine.entity.Status;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DetailedStatisticsItemTest {

    @Test
    public void getDetailedStatisticsItemTest() {
        DetailedStatisticsItem detailedStatisticsItem = new DetailedStatisticsItem();
        detailedStatisticsItem.setName("name");
        detailedStatisticsItem.setStatus(Status.INDEXING);
        detailedStatisticsItem.setUrl("url");
        detailedStatisticsItem.setStatusTime(LocalDateTime.of(2022, 1, 1, 1, 1, 1));
        detailedStatisticsItem.setError("error");
        detailedStatisticsItem.setPages(1);
        detailedStatisticsItem.setLemmas(1);
        assertThat("error", equalTo(detailedStatisticsItem.getError()));
        assertThat(1, equalTo(detailedStatisticsItem.getPages()));
        assertThat(1, equalTo(detailedStatisticsItem.getLemmas()));
        assertThat(LocalDateTime.of(2022, 1, 1, 1, 1, 1), equalTo(detailedStatisticsItem.getStatusTime()));
        assertThat("url", equalTo(detailedStatisticsItem.getUrl()));
        assertThat("name", equalTo(detailedStatisticsItem.getName()));
        assertThat(Status.INDEXING, equalTo(detailedStatisticsItem.getStatus()));
    }
}
