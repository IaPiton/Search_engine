package searchengine.services.datebase.lemma;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.hibernate.NonUniqueResultException;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import searchengine.entity.Lemma;
import searchengine.entity.Page;
import searchengine.entity.Site;
import searchengine.repository.LemmaRepository;
import searchengine.utils.MorphologyParse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log4j2

@RequiredArgsConstructor
public class LemmaEntityImpl implements LemmaEntity {
    private final LemmaRepository lemmaRepository;
    private final MorphologyParse morphologyParse;

    @Override

    public Long countLemma() {
        return lemmaRepository.count();
    }

    @Override

    public Integer countLemmaBySiteId(Integer siteId) {
        return lemmaRepository.countBySiteId(siteId);
    }

    @Override
    public Map<Integer, Integer> createLemma(Site site, Page page) {
        Map<String, Integer> lemmas = morphologyParse.getLemmasFromText(page.getContent());
        return saveLemma(lemmas, site);
    }

    @Override
    public Lemma findById(Integer id) {
        return lemmaRepository.findById(Long.valueOf(id)).get();
    }


    public Map<Integer, Integer> saveLemma(Map<String, Integer> lemmaMap, Site site) {
        Map<Integer, Integer> index = new HashMap<>();
        for (String lemmas : lemmaMap.keySet()) {
            Lemma lemma = new Lemma();
            if (!existsByLemmaAndSite(lemmas, site)) {
                lemma.setLemma(lemmas);
                lemma.setSite(site);
                lemma.setFrequency(1);
            } else {
                lemma = findFirstByLemmaAndSite(lemmas, site);
                lemma.setFrequency(lemma.getFrequency() + 1);
            }
            lemmaRepository.saveAndFlush(lemma);
            index.put(lemma.getId(), lemmaMap.get(lemmas));
        }

        return index;
    }


    public boolean existsByLemmaAndSite(String lemmas, Site site) {
        return lemmaRepository.existsByLemmaAndSite(lemmas, site);
    }

    public Lemma findFirstByLemmaAndSite(String lemma, Site site) {
        return lemmaRepository.findFirstByLemmaAndSite(lemma, site);
    }
}








