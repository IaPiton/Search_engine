package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import searchengine.entity.Indexes;
import searchengine.entity.Lemma;

import java.util.List;

@Repository
public interface IndexesRepository extends JpaRepository<Indexes, Integer> {

    void deleteByLemmaId(Integer integer);
}
