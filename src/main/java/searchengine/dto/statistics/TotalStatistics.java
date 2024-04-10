package searchengine.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TotalStatistics {
    private Long sites;
    private Long pages;
    private Long lemmas;
    private boolean indexing;
}
