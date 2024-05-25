package searchengine.services.datebase.lemma;

import lombok.RequiredArgsConstructor;


import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;
import searchengine.entity.Lemma;
import searchengine.entity.Page;
import searchengine.entity.Site;
import searchengine.repository.LemmaRepository;
import searchengine.utils.MorphologyParse;


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
    @Transactional(readOnly = true)
    public Long countLemma() {
        return lemmaRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Integer countLemmaBySiteId(Integer siteId) {
        return lemmaRepository.countBySiteId(siteId);
    }

    @Override
    public Map<Integer, Integer> createLemma(Site site, Page page) {
        Map<String, Integer> lemmas = morphologyParse.getLemmasFromText(page.getContent());
        return saveLemma(lemmas, site);
    }

    public Map<Integer, Integer> saveLemma(Map<String, Integer> lemmaMap, Site site) {
        Map<Integer, Integer> index = new HashMap<>();
        try {
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
                saveLemma(lemma);
                index.put(lemma.getId(), lemmaMap.get(lemmas));
            }
        } catch (Exception e) {
            log.error(e);
        }
        return index;
    }

    @Override
    @Transactional(readOnly = true)
    public Lemma findById(Integer id) {
        return lemmaRepository.findById(id);
    }

    @Transactional
    public void saveLemma(Lemma lemma) {
        lemmaRepository.saveAndFlush(lemma);
    }

    @Transactional(readOnly = true)
    public boolean existsByLemmaAndSite(String lemmas, Site site) {
        return lemmaRepository.existsByLemmaAndSite(lemmas, site);
    }

    @Transactional(readOnly = true)
    public Lemma findFirstByLemmaAndSite(String lemma, Site site) {
        return lemmaRepository.findFirstByLemmaAndSite(lemma, site);
    }

    @Transactional(readOnly = true)
    public List<Lemma> findLemmaByPageId(Page page) {
        return lemmaRepository.findLemmaByPageId(page);
    }

    @Override
    @Transactional
    public void deleteLemmaByPage(List<Lemma> lemmaByPage) {
        lemmaByPage.forEach(lemma -> {
            if ((lemma.getFrequency() <= 1)) {
                lemmaRepository.delete(lemma);
            } else {
                lemma.setFrequency(lemma.getFrequency() - 1);
                lemmaRepository.saveAndFlush(lemma);
            }
        });
    }

    @Override
    public void deleteAllLemma() {
        lemmaRepository.deleteAll();
    }
}








