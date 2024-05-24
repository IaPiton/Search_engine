package searchengine.services.datebase.site;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Site;
import searchengine.entity.Status;
import searchengine.mapper.PageMapper;
import searchengine.repository.SiteRepository;

import java.net.MalformedURLException;
import java.net.URL;

@Component
@Log4j2
@RequiredArgsConstructor
public class SiteEntityImpl implements SiteEntity {
    private final SiteRepository siteRepository;
    private final PageMapper pageMapper;

    @Override
    @Transactional
    public Site createSite(SiteDto siteDto, Status status, String error) {
        siteDto.setStatus(status);
        siteDto.setLastError(error);
        return siteRepository.saveAndFlush(pageMapper.siteDtoToSite(siteDto));
    }

    @Override
    @Transactional
    public void updateStatusAndErrorSite(Site site, Status status, String error) {
        site.setStatus(status);
        site.setLastError(error);
        siteRepository.saveAndFlush(site);
    }

    @Override
    @Transactional(readOnly = true)
     public Integer countSitesByStatus(Status status) {
        return siteRepository.countByStatus(status);
    }


    @Override
    @Transactional(readOnly = true)
    public Long countSite() {
        return siteRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Site> getAllSites() {
        return siteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
     public Site getSiteByUrl(String url)  {
        url = getUrl(url);
        Site site = siteRepository.findByUrl(url);
        return site;
    }

    @Override
    @Transactional
    public void deleteAllSite() {
        siteRepository.deleteAll();
    }


    private String getUrl(String url) {
        URL urlPath;
        try {
            urlPath = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return "https://" + urlPath.getHost() ;
    }
}
