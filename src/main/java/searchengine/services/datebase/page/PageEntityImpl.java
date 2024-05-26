package searchengine.services.datebase.page;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

    @SneakyThrows
    @Override
    public void deletePageByPath(String urlReplace) {
        String path = createPath(urlReplace);
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
    public Page createPage(Site site, String url, Integer code, String content) throws MalformedURLException {
        String path = createPath(url);
        content.replace("\u0000", "");
        Page page = new Page();
        page.setPath(path);
        page.setCode(code);
        page.setContent(content);
        page.setSite(site);
        return savePage(page);
    }


    @Override
    @Transactional(readOnly = true)
    public Page findPageByUrl(String urlReplace) throws MalformedURLException {
        String path = createPath(urlReplace);
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

    private String createPath(String path) throws MalformedURLException {
        URL urlPath = new URL(path);
        return urlPath.getPath().isEmpty() ? "path/" : urlPath.getPath();

    }
}
