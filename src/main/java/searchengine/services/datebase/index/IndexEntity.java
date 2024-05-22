package searchengine.services.datebase.index;

import searchengine.entity.Lemma;
import searchengine.entity.Page;
import searchengine.entity.Site;

import java.util.Map;

public interface IndexEntity {
    public void createIndex(Map<Integer, Integer> index, Site site, Page page);
}
