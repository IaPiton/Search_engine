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
import java.util.Map;


@Log4j2
@Service
@RequiredArgsConstructor
public class DateBaseService {

    private final SiteEntity siteEntity;
    private final PageEntity pageEntity;
    private final LemmaEntity lemmaEntity;
    private final IndexEntity indexEntity;



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


    public void createPage(String url, Integer code, String content) throws MalformedURLException {
        Site site = siteEntity.getSiteByUrl(url);
        Page page = pageEntity.createPage(site, url, code, content);
        if(page.getCode() == 200){
          Map<Integer, Integer> index = lemmaEntity.createLemma(site, page);
          indexEntity.createIndex(index, page.getSite(), page);
        }
    }

        public void deleterAll() {
        siteEntity.deleteAllSite();
    }

    public Lemma lemmaFindById(Integer id){
        return lemmaEntity.findById(id);
    }


//
//    @Transactional
//    public PageDto savePage(Page page) {
//        return pageMapper.pageToPageDto(pageRepository.saveAndFlush(page));
//    }


//    public void createLemma(Integer siteId, PageDto pageDto) {
//        try {
//            HashMap<String, Integer> lemmaMap = (HashMap<String, Integer>) morphologyParse.collectLemmas(pageDto.getContent());
//            List<Lemma> lemmaList = new ArrayList<>();
//            List<Lemma> lemmaUpdate = new ArrayList<>();
//            for (Map.Entry<String, Integer> entry : lemmaMap.entrySet()) {
//                if (existByLemmaBySite(siteId, entry.getKey())) {
//                    Lemma lemma = lemmaByLemmaBySiteId(siteId, entry.getKey());
//                    lemma.setFrequency(lemma.getFrequency() + entry.getValue());
//                    lemmaUpdate.add(lemma);
//                    continue;
//                }
//                Lemma lemma = new Lemma();
//                lemma.setLemma(entry.getKey());
//                lemma.setFrequency(entry.getValue());
//                lemma.setSite(siteRepository.findById(Long.valueOf(siteId)).get());
//                lemmaList.add(lemma);
//            }
//            saveLemma(lemmaList);
//            saveLemmaUpdate(lemmaUpdate);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

//    @Transactional
//    public void saveLemma(List<Lemma> lemmaList) {
//        lemmaRepository.saveAll(lemmaList);
//    }

//    @Transactional
//    public void saveLemmaUpdate(List<Lemma> lemmaList) {
//        lemmaList.forEach(lemma -> lemmaRepository.updateAll(lemma.getFrequency(), lemma.getId()));
//
//    }

//    @Transactional(readOnly = true)
//    public Lemma lemmaByLemmaBySiteId(Integer siteId, String key) {
//        List<Lemma> lemmaList = lemmaRepository.findByLemmaBySiteId(key, siteId);
//        return lemmaList.get(0);
//    }

//    @Transactional(readOnly = true)
//    public boolean existByLemmaBySite(Integer siteId, String key) {
//        if (lemmaRepository.existsByLemmaBySiteId(key, siteId)) {
//            return true;
//        }
//        return false;
//    }


//    public void updateStatusSite(SiteDto siteDto, Status status) {
//        Site site = getSiteByName(siteDto.getName());
//        site.setStatus(status);
//        siteRepository.save(site);
//    }


//    @Transactional
//
//    public void updateErrorSite(SiteDto siteDto, String error) {
//        Site site = getSiteByName(siteDto.getName());
//        site.setLastError(error + " " + pageRepository.countByCode(404) + " pages");
//        siteRepository.save(site);
//    }

//    @Transactional(readOnly = true)
//    public Site getSiteByName(String name) {
//        return siteRepository.findByName(name);
//    }













//    public void deleteAllSite() {
//        siteRepository.deleteAll();
//        log.info("Очистка сайтов успешна");
//    }


//    public void deleteAllPage() {
//        pageRepository.deleteAllPages();
//        log.info("Очистка страниц успешна");
//    }


//
//
//    }








}


