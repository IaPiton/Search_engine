package searchengine.services.datebase;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import searchengine.dto.page.PageDto;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Site;
import searchengine.entity.Status;
import searchengine.mapper.PageMapper;
import searchengine.mapper.SiteMapper;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;



@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class DateBaseService {

    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;
    private final SiteMapper siteMapper;
    private final PageMapper pageMapper;


    public void createPage(String path, Integer code, String content, SiteDto siteDto) {
        try {
            PageDto pageDto = new PageDto();
            pageDto.setPath(path);
            pageDto.setCode(code);
            pageDto.setContent(content);
            pageDto.setSite(siteMapper.siteDtoToSite(siteDto));
            pageRepository.saveAndFlush(pageMapper.pageDtoToPage(pageDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Transactional(readOnly = true)
    public Integer countSitesByStatus(Status status) {
        return siteRepository.countByStatus(status);
    }


    public SiteDto createSite(SiteDto siteDto, Status status, String error) {
        siteDto.setStatus(status);
        siteDto.setLastError(error);
        return siteMapper.siteToSiteDto(siteRepository.save(siteMapper.siteDtoToSite(siteDto)));
    }


    public void updateStatusSite(SiteDto siteDto, Status status) {
        Site site = getSiteByName(siteDto.getName());
        site.setStatus(status);
        siteRepository.save(site);
    }


    public void updateErrorSite(SiteDto siteDto, String error) {
        Site site = getSiteByName(siteDto.getName());
        site.setLastError(error);
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

    @Transactional(readOnly = true)
    public void deleteAllPage() {
        pageRepository.deleteAllPages();
        log.info("Очистка страниц успешна");
    }


}


