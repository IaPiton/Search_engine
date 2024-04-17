package searchengine.dto.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TotalStatistics {
    private Long sites;
    private Long pages;
    private Long lemmas;
    private boolean indexing;
}
