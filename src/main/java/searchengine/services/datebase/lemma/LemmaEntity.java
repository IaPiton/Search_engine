package searchengine.services.datebase.lemma;

import searchengine.entity.Lemma;
import searchengine.entity.Page;
import searchengine.entity.Site;

import java.util.List;
import java.util.Map;

public interface LemmaEntity {

    Long countLemma();

    Integer countLemmaBySiteId(Integer siteId);

    Map<Integer,Integer> createLemma(Site site, Page page);

    Lemma findById(Integer id);

    List<Lemma> findLemmaByPageId(Page page);


    void deleteLemmaByPage(List<Lemma> lemmaByPage);

    void deleteAllLemma();
}
