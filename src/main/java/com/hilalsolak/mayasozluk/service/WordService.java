package com.hilalsolak.mayasozluk.service;

import com.hilalsolak.mayasozluk.model.dto.WordRequest;
import com.hilalsolak.mayasozluk.model.dto.WordResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface WordService {
    List<WordResponse> getAllWords();

    WordResponse getWordById(UUID id);

    WordResponse createWord(WordRequest request);

    WordResponse updateWordById(UUID id, WordRequest request);
    void deleteWordById(UUID id);
    List<WordResponse> getCategoriesBySearchText(@RequestParam String filter);

    void increaseWordSearchCount(String wordName);
}

