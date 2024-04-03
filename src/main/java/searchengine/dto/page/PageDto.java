package searchengine.dto.page;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Site;



@Data
public class PageDto {
    private Integer id;
    private String path;
    private Integer code;
    private String content;
    private Site site;

}
