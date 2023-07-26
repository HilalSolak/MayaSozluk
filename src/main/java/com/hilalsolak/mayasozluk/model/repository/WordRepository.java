package com.hilalsolak.mayasozluk.model.repository;

import com.hilalsolak.mayasozluk.model.entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface WordRepository extends JpaRepository<Word, UUID> {
    boolean existsByWord(String word);

    @Query(value = "SELECT * FROM word ORDER BY name ASC", nativeQuery = true)
    List<Word> findAllByWordAsc();

    Word findWordByWord(String wordName);

}
