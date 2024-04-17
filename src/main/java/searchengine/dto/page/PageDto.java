package searchengine.dto.page;


import lombok.*;
import searchengine.dto.site.SiteDto;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
    private Integer id;
    private String path;
    private Integer code;
    private String content;
    private SiteDto siteDto;




}
