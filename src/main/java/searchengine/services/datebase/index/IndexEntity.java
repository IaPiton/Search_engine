package searchengine.services.datebase.index;

import searchengine.entity.Page;
import searchengine.entity.Site;

import java.util.Map;

public interface IndexEntity {
    void createIndex(Map<Integer, Integer> index, Site site, Page page);

    void deleteIndexesByPage(Page page);

    void deleteAllIndex();
}
