package searchengine.services.datebase.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import searchengine.entity.Page;
import searchengine.entity.Site;
import searchengine.repository.PageRepository;

import java.net.MalformedURLException;
import java.net.URL;
@Component

@RequiredArgsConstructor
public class PageEntityImpl implements PageEntity {
    private final PageRepository pageRepository;
    @Override
    public void deletePageByPath(String urlReplace) {
        String path = null;
        try {
            path = createPath(urlReplace);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        if (existsByPath(path)) {
           deleteByPath(path);
        }
    }

    @Override

    public Long countPage() {
        return pageRepository.count();
    }

    @Override

    public Integer countPageBySiteId(Integer siteId) {
        return pageRepository.countBySiteId(siteId);
    }

    @Override

    public Page createPage(Site site, String url,Integer code, String content) {
        String path = null;
        Page page = new Page();
        try {
            path = createPath(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        if (!existsByPath(path)) {
            page.setPath(path);
            page.setCode(code);
            page.setContent(content);
            page.setSite(site);
            return savePage(page);
        }
        return pageByPath(path, site);
    }







    public boolean existsByPath(String path) {
        return pageRepository.existsByPath(path);
    }



    public void deleteByPath(String path) {
        pageRepository.deleteByPath(path);
    }


    public Page savePage(Page page) {
        return pageRepository.saveAndFlush(page);
    }


    public Page pageByPath(String path, Site site) {
        return pageRepository.findByPathAndSite(path, site);
    }

    private String createPath(String path) throws MalformedURLException {
        URL urlPath = new URL(path);
        return urlPath.getPath();
    }
}
