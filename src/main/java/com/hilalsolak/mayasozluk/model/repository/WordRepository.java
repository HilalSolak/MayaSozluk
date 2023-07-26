package com.hilalsolak.mayasozluk.model.repository;

import com.hilalsolak.mayasozluk.model.entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WordRepository extends JpaRepository<Word, UUID> {
}
