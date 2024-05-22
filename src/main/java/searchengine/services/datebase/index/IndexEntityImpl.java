package searchengine.services.datebase.index;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import searchengine.entity.Indexes;
import searchengine.entity.Lemma;
import searchengine.entity.Page;
import searchengine.entity.Site;
import searchengine.repository.IndexesRepository;
import searchengine.services.datebase.DateBaseService;
import searchengine.services.datebase.lemma.LemmaEntity;

import java.util.Map;
@Component

@RequiredArgsConstructor
public class IndexEntityImpl implements IndexEntity {
    private final LemmaEntity lemmaEntity;
    private final IndexesRepository indexesRepository;


    @Override
    public void createIndex(Map<Integer, Integer> indexes, Site site, Page page) {
        for (Integer lemmaIndex : indexes.keySet()) {
            Indexes index = new Indexes();
            index.setPage(page);
            index.setLemma(lemmaEntity.findById(lemmaIndex));
            index.setRank(indexes.get(lemmaIndex));
            indexesRepository.saveAndFlush(index);
        }
    }
}
