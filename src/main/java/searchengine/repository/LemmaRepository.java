package searchengine.repository;

import org.hibernate.query.spi.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.entity.Lemma;
import searchengine.entity.Site;

import javax.net.ssl.SSLSession;
import java.util.List;


@Repository
public interface LemmaRepository extends JpaRepository<Lemma, Long> {

    Integer countBySiteId(Integer siteId);














    boolean existsByLemmaAndSite(String lemmas, Site site);


    Lemma findFirstByLemmaAndSite(String lemma, Site site);


//    @Modifying(flushAutomatically = true)
//    @Query(value = " update lemma set frequency=?,lemma=?,site_id=? where id=?", nativeQuery = true)
//    void updateLemma(Integer frequency, String lemma, Integer siteId, Integer id);
//
//    @Modifying(flushAutomatically = true)
//    @Query(value = "insert into lemma (lemma, frequency, site_id) values (?1, ?2, ?3)", nativeQuery = true)
//    void insertLemma(String lemma, Integer frequency, Integer siteId);
}
