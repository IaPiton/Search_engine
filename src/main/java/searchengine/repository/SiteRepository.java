package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.entity.Site;
import searchengine.entity.Status;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {

    int countByStatus(Status status);


    Site findByName(String name);
}
