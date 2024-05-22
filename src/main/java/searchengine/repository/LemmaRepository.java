package searchengine.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import searchengine.entity.Lemma;
import searchengine.entity.Page;
import searchengine.entity.Site;


import java.util.List;


@Repository
public interface LemmaRepository extends JpaRepository<Lemma, Long> {

    Integer countBySiteId(Integer siteId);

    Lemma findById(Integer id);

    boolean existsByLemmaAndSite(String lemmas, Site site);


    Lemma findFirstByLemmaAndSite(String lemma, Site site);

    @Query(value = "select l from Lemma l join Indexes i on l.id = i.lemma.id where i.page in :page")
    List<Lemma> findLemmaByPageId(Page page);



}
