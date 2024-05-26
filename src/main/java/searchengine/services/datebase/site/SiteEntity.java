package searchengine.services.datebase.site;

import searchengine.dto.site.SiteDto;
import searchengine.entity.Site;
import searchengine.entity.Status;

import java.net.MalformedURLException;
import java.util.List;

public interface SiteEntity {

    Site createSite(SiteDto siteDto, Status status, String error);

   void updateStatusAndErrorSite(Site site, Status status, String error);
    Integer countSitesByStatus(Status status);

     Long countSite();

    List<Site> getAllSites();

    Site getSiteByUrl(String url) throws MalformedURLException;
    void deleteAllSite();


}
