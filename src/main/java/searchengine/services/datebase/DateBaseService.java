package searchengine.services.datebase;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import searchengine.dto.page.PageDto;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Site;
import searchengine.entity.Status;
import searchengine.mapper.PageMapper;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

import java.net.MalformedURLException;
import java.net.URL;


@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class DateBaseService {

    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;
    private final PageMapper pageMapper;


    public void createPage(String url, Integer code, String content) throws MalformedURLException {
        String path = createPath(url);
        SiteDto siteDto = pageMapper.siteToSiteDto(getSiteByUrl(url));
        if(!pageRepository.existsByPath(path)) {
            PageDto pageDto = new PageDto();
            pageDto.setPath(path);
            pageDto.setCode(code);
            pageDto.setContent(content);
            pageDto.setSiteDto(siteDto);
            pageRepository.saveAndFlush(pageMapper.pageDtoToPage(pageDto));
        }
    }




    public SiteDto createSite(SiteDto siteDto, Status status, String error) {
        siteDto.setStatus(status);
        siteDto.setLastError(error);
        return pageMapper.siteToSiteDto(siteRepository.save(pageMapper.siteDtoToSite(siteDto)));
    }

    public void updateStatusSite(SiteDto siteDto, Status status) {
        Site site = getSiteByName(siteDto.getName());
        site.setStatus(status);
        siteRepository.save(site);
    }


    @Transactional(readOnly = true)
    public Integer countSitesByStatus(Status status) {
        return siteRepository.countByStatus(status);
    }

    public void updateErrorSite(SiteDto siteDto, String error) {
        Site site = getSiteByName(siteDto.getName());
        site.setLastError(error + " " + pageRepository.countByCode(404) + " pages");
        siteRepository.save(site);
    }

    @Transactional(readOnly = true)
    public Site getSiteByName(String name) {
        return siteRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Long countSite() {
        return siteRepository.count();
    }

    @Transactional(readOnly = true)
    public Long countPage() {
        return pageRepository.count();
    }

    @Transactional(readOnly = true)
    public Iterable<Site> getAllSites() {
        return siteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Integer countPageBySiteId(Integer siteId) {
        return pageRepository.countBySiteId(siteId);
    }


    public void deleterAll() {
        deleteAllPage();
        deleteAllSite();
    }


    public void deleteAllSite() {
        siteRepository.deleteAll();
        log.info("Очистка сайтов успешна");
    }


    public void deleteAllPage() {
        pageRepository.deleteAllPages();
        log.info("Очистка страниц успешна");
    }


    public void deletePage(String urlReplace) throws MalformedURLException {
       String path = createPath(urlReplace);
        if (pageRepository.existsByPath(path)) {
            pageRepository.deleteByPath(path);
        }


    }

    private String createPath(String path) throws MalformedURLException {
        URL urlPath = new URL(path);
        return urlPath.getPath();
    }
    private Site getSiteByUrl(String url) throws MalformedURLException {
        URL urlPath = new URL(url);
        return siteRepository.findByUrl("https://" + urlPath.getHost());
    }
}


