package searchengine.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import searchengine.entity.Page;
import searchengine.entity.Site;




@Repository
public interface PageRepository extends JpaRepository<Page, Long> {

    boolean existsByPathAndSite(String url, Site site);
   

    Integer countBySiteId(Integer id);

    Page findByPathAndSite(String path, Site site);

    Page findByPath(String path);

    boolean existsByPath(String path);

    @Override
    @Modifying
    @Query("delete from Page ")
    void deleteAll();
}
