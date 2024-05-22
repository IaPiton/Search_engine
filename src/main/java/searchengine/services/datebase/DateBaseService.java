package searchengine.services.datebase;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import searchengine.dto.site.SiteDto;

import searchengine.entity.Lemma;
import searchengine.entity.Page;
import searchengine.entity.Site;
import searchengine.entity.Status;


import searchengine.services.datebase.index.IndexEntity;
import searchengine.services.datebase.lemma.LemmaEntity;
import searchengine.services.datebase.page.PageEntity;
import searchengine.services.datebase.site.SiteEntity;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;


@Log4j2
@Service
@RequiredArgsConstructor
public class DateBaseService {

    private final SiteEntity siteEntity;
    private final PageEntity pageEntity;
    private final LemmaEntity lemmaEntity;
    private final IndexEntity indexEntity;

    public void createPage(String url, Integer code, String content) throws MalformedURLException {
        Site site = siteEntity.getSiteByUrl(url);
        Page page = pageEntity.createPage(site, url, code, content);
        if (page.getCode() == 200) {
            Map<Integer, Integer> index = lemmaEntity.createLemma(site, page);
            indexEntity.createIndex(index, page.getSite(), page);
        }
    }

    public Site createSite(SiteDto siteDto, Status status, String error) {
        return siteEntity.createSite(siteDto, status, error);
    }

    public void updateStatusAndErrorSite(Site site, Status status, String error) {
        siteEntity.updateStatusAndErrorSite(site, status, error);
    }

    public Integer countSitesByStatus(Status status) {
        return siteEntity.countSitesByStatus(status);
    }

    public void deletePageByPath(String urlReplace) {
        pageEntity.deletePageByPath(urlReplace);
    }

    public Long countSite() {
        return siteEntity.countSite();
    }

    public Long countPage() {
        return pageEntity.countPage();
    }

    public Long countLemma() {
        return lemmaEntity.countLemma();
    }

    public Iterable<Site> getAllSites() {
        return siteEntity.getAllSites();
    }

    public Integer countPageBySiteId(Integer siteId) {
        return pageEntity.countPageBySiteId(siteId);
    }

    public Integer countLemmaBySiteId(Integer siteId) {
        return lemmaEntity.countLemmaBySiteId(siteId);
    }


    public void deleterAll() {
        siteEntity.deleteAllSite();
    }

    public List<Lemma> findLemmaByPageId(Page page){
        return lemmaEntity.findLemmaByPageId(page);
    }

    public Page findPageByUrl(String urlReplace) {
        return pageEntity.findPageByUrl(urlReplace);
    }

    public void deleteLemmaByPage(List<Lemma> lemmaByPage) {
        lemmaEntity.deleteLemmaByPage(lemmaByPage);
    }

    public void deleteIndexesByLemma(List<Lemma> lemmaByPage) {
        indexEntity.deleteIndexesByLemma(lemmaByPage);
    }
}


