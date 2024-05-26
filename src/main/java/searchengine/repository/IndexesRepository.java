package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import searchengine.entity.Indexes;
import searchengine.entity.Page;

@Repository
public interface IndexesRepository extends JpaRepository<Indexes, Integer> {

    void deleteByLemmaId(Integer integer);

    @Override
    @Modifying
    @Query("delete from Indexes ")
    void deleteAll();

    void deleteByPage(Page page);
}
