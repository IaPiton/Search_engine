package searchengine.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import searchengine.entity.Page;


@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
    Integer countBySiteId(Integer id);

    void deleteAll();
}
