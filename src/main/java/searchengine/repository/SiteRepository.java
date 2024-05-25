package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import searchengine.entity.Site;
import searchengine.entity.Status;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

    @Override
    @Modifying
    @Query("delete from Site")
    void deleteAll();

    int countByStatus(Status status);

    Site findByName(String name);

    Site findByUrl(String url);



}
