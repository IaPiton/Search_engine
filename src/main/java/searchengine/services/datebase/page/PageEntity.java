package searchengine.services.datebase.page;

import org.springframework.transaction.annotation.Transactional;
import searchengine.entity.Page;
import searchengine.entity.Site;

import java.net.MalformedURLException;

public interface PageEntity {
    public void deletePageByPath(String urlReplace);

    public Long countPage();

    public Integer countPageBySiteId(Integer siteId);

    public Page createPage(Site site, String url, Integer code, String content);






}
