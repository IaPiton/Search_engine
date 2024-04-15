package searchengine.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.entity.Page;

import java.util.List;


@Repository
@Transactional
public interface PageRepository extends JpaRepository<Page, Long> {
    Integer countBySiteId(Integer id);

    void deleteAll();

    @Query("select p.id from Page p")
    List<Long> findIds();


    @Modifying
    @Query("delete from Page p where p.id in ?1")
    void deletePage(@Param("id") List<Long> ids);

    @Modifying
    @Query(value = "DELETE FROM Page", nativeQuery = true)
    void deleteAllPages();
}
