package searchengine.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import searchengine.entity.Status;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DetailedStatisticsItem {
    private String url;
    private String name;
    private Status status;
    private LocalDateTime statusTime;
    private String error;
    private Integer pages;
    private Integer lemmas;
}
