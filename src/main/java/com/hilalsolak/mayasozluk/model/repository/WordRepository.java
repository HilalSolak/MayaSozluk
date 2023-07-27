package com.hilalsolak.mayasozluk.model.repository;

import com.hilalsolak.mayasozluk.model.entities.Word;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface WordRepository extends JpaRepository<Word, UUID> {

    boolean existsByMayaWord(String mayaWord);

    @Query(value = "SELECT * FROM t_words ORDER BY maya_word ASC", nativeQuery = true)
    List<Word> findAllByMayaWordAsc();

    Word findWordByMayaWord(String wordName);

}
