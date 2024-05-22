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

    boolean existsByPath(String url);
    @Modifying
    @Query(value = "DELETE FROM Page p WHERE p.path = :path", nativeQuery = true)
    void deleteByPath(String path);

    Integer countBySiteId(Integer id);

    Page findByPathAndSite(String path, Site site);


//    @Query("select p.id from Page p")
//    List<Long> findIds();


//    @Modifying
//    @Query("delete from Page p where p.id in ?1")
//    void deletePage(@Param("id") List<Long> ids);
//
//    @Modifying
//    @Query(value = "DELETE FROM Page", nativeQuery = true)
//    void deleteAllPages();
//
//    Integer countByCode(int i);
//
//
//
//
//    ;
}
