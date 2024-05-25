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
        String path;
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
    @Transactional(readOnly = true)
    public Long countPage() {
        return pageRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer countPageBySiteId(Integer siteId) {
        return pageRepository.countBySiteId(siteId);
    }

    @Override
    public Page createPage(Site site, String url, Integer code, String content) {
        String path = null;
           content.replace("\u0000", "");
           Page page = new Page();
           try {
               path = createPath(url);
           } catch (MalformedURLException e) {
               throw new RuntimeException(e);
           }
           if (!pageRepository.existsByPathAndSite(path, site)) {
               page.setPath(path);
               page.setCode(code);
               page.setContent(content);
               page.setSite(site);
               return savePage(page);
           }
        page = pageByPath(path, site);
        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public Page findPageByUrl(String urlReplace) {
        String path = null;
        try {
            path = createPath(urlReplace);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        if (existsByPath(path)) {
            return pageRepository.findByPath(path);
        }
        return null;
    }

    @Override
    public void deleteAllPage() {
        pageRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public boolean existsByPath(String path) {
        return pageRepository.existsByPath(path);
    }


    @Transactional
    public void deleteByPath(String path) {
        pageRepository.delete(pageRepository.findByPath(path));
    }

    @Transactional
    public Page savePage(Page page) {
        return pageRepository.saveAndFlush(page);
    }

    @Transactional(readOnly = true)
    public Page pageByPath(String path, Site site) {
        return pageRepository.findByPathAndSite(path, site);
    }

    private String createPath(String path) throws MalformedURLException {
        URL urlPath = new URL(path);
        return urlPath.getPath();
    }
}
