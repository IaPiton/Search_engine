package searchengine.services.datebase.index;

import searchengine.entity.Lemma;
import searchengine.entity.Page;
import searchengine.entity.Site;

import java.util.List;
import java.util.Map;

public interface IndexEntity {
    void createIndex(Map<Integer, Integer> index, Site site, Page page);

    void deleteIndexesByLemma(List<Lemma> lemmaByPage);

    void deleteAllIndex();
}
