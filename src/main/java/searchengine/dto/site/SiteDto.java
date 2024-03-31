package searchengine.dto.site;

import lombok.Data;
import searchengine.entity.Page;
import searchengine.entity.Status;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class SiteDto {
    private Integer id;
    private Status status;
    private LocalDateTime statusTime;
    private String lastError;
    private String url;
    private String name;
    private Set<Page> pages = new HashSet<>();
}
