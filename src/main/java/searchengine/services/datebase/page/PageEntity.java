package searchengine.services.datebase.page;


import searchengine.entity.Page;
import searchengine.entity.Site;

import java.net.MalformedURLException;


public interface PageEntity {
    void deletePageByPath(String urlReplace);

    Long countPage();

    Integer countPageBySiteId(Integer siteId);

    Page createPage(Site site, String url, Integer code, String content) throws MalformedURLException;

    Page findPageByUrl(String urlReplace) throws MalformedURLException;

    void deleteAllPage();
}
