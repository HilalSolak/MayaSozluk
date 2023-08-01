package com.hilalsolak.mayasozluk.service;

import com.hilalsolak.mayasozluk.model.dto.requests.WordMeaningRequest;
import com.hilalsolak.mayasozluk.model.dto.requests.WordRequest;
import com.hilalsolak.mayasozluk.model.dto.responses.WordResponse;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

public interface WordService {
    List<WordResponse> getAllWords();

    WordResponse getWordById(UUID id);
    WordResponse getWordByName(String name);

    WordResponse createWord(WordRequest request);

    WordResponse updateWordById(UUID id, WordRequest request);

    void deleteWordById(UUID id);

    List<WordResponse> getWordsBySearchText(@RequestParam String filter);

    void increaseWordSearchCount(String wordName);

    WordResponse createMeaningRequest(WordMeaningRequest request);
}

