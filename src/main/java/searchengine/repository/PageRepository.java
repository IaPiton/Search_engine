package searchengine.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.entity.Page;
import searchengine.entity.Site;

import java.util.List;


@Repository
public interface PageRepository extends JpaRepository<Page, Long> {

    boolean existsByPathAndSite(String url, Site site);
   

    Integer countBySiteId(Integer id);

    Page findByPathAndSite(String path, Site site);

    Page findByPath(String path);

    boolean existsByPath(String path);
}
