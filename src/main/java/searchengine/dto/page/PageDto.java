package searchengine.dto.page;

import lombok.Data;


import searchengine.entity.Site;



@Data
public class PageDto {
    private Integer id;
    private String path;
    private Integer code;
    private String content;
    private Site site;

}
