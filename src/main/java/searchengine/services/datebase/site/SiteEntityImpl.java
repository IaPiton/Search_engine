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
    public Site createSite(SiteDto siteDto, Status status, String error) {
        siteDto.setStatus(status);
        siteDto.setLastError(error);
        return siteRepository.saveAndFlush(pageMapper.siteDtoToSite(siteDto));
    }

    @Override

    public void updateStatusAndErrorSite(Site site, Status status, String error) {
        site.setStatus(status);
        site.setLastError(error);
        siteRepository.saveAndFlush(site);
    }

    @Override
     public Integer countSitesByStatus(Status status) {
        return siteRepository.countByStatus(status);
    }


    @Override
    public Long countSite() {
        return siteRepository.count();
    }

    @Override
    public Iterable<Site> getAllSites() {
        return siteRepository.findAll();
    }

    @Override
     public Site getSiteByUrl(String url)  {
        return siteRepository.findByUrl(getUrl(url));
    }

    @Override
    public void deleteAllSite() {
        siteRepository.deleteAll();
    }

    @Override
    public Site getSiteByName(String name) {
        return null;
    }

    private String getUrl(String url) {
        URL urlPath = null;
        try {
            urlPath = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return "https://" + urlPath.getHost() ;
    }
}
