package searchengine.services.datebase.lemma;

import searchengine.entity.Lemma;
import searchengine.entity.Page;
import searchengine.entity.Site;

import java.util.List;
import java.util.Map;

public interface LemmaEntity {

    public Long countLemma();

    public Integer countLemmaBySiteId(Integer siteId);

    public Map<Integer,Integer> createLemma(Site site, Page page);

    public Lemma findById(Integer id);

    public List<Lemma> findLemmaByPageId(Page page);


    void deleteLemmaByPage(List<Lemma> lemmaByPage);
}
