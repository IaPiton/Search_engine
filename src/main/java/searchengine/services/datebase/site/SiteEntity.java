package searchengine.services.datebase.site;

import searchengine.dto.site.SiteDto;
import searchengine.entity.Site;
import searchengine.entity.Status;

import java.net.MalformedURLException;

public interface SiteEntity {

    public Site createSite(SiteDto siteDto, Status status, String error);

    public void updateStatusAndErrorSite(Site site, Status status, String error);
    public Integer countSitesByStatus(Status status);

    public Long countSite();

    Iterable<Site> getAllSites();

    public Site getSiteByUrl(String url) throws MalformedURLException;
    public void deleteAllSite();

    public Site getSiteByName(String name);
}
