package searchengine.services.datebase.page;


import searchengine.entity.Page;
import searchengine.entity.Site;



public interface PageEntity {
    void deletePageByPath(String urlReplace);

    Long countPage();

    Integer countPageBySiteId(Integer siteId);

    Page createPage(Site site, String url, Integer code, String content);

    Page findPageByUrl(String urlReplace);

    void deleteAllPage();
}
