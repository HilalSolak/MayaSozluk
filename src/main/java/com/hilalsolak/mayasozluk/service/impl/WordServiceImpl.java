package com.hilalsolak.mayasozluk.service.impl;

import com.hilalsolak.mayasozluk.converters.ConverterResponse;
import com.hilalsolak.mayasozluk.model.dto.requests.WordMeaningRequest;
import com.hilalsolak.mayasozluk.model.dto.requests.WordRequest;
import com.hilalsolak.mayasozluk.model.dto.responses.WordResponse;
import com.hilalsolak.mayasozluk.model.entities.Word;
import com.hilalsolak.mayasozluk.model.repository.WordRepository;
import com.hilalsolak.mayasozluk.service.WordService;
import com.hilalsolak.mayasozluk.utils.advice.exceptions.EntityAlreadyExistsException;
import com.hilalsolak.mayasozluk.utils.advice.exceptions.EntityNotFoundException;
import com.hilalsolak.mayasozluk.utils.constants.GlobalConstants;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@CacheConfig(cacheNames = "BI_Words")
public class WordServiceImpl implements WordService {
    private final WordRepository repository;

    public WordServiceImpl(WordRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<WordResponse> getAllWords() {
        List<WordResponse> response = repository.findAll().stream().map(ConverterResponse::convert).toList();

        return response;
    }

    @Override
    public WordResponse getWordById(UUID id) {
        Word word = getWordByIdInRepository(id);
        WordResponse response = ConverterResponse.convert(word);

        return response;
    }
    @Override
    public WordResponse getWordByName(String name) {
        Word word = getWordByNameInRepository(name);
        WordResponse response = ConverterResponse.convert(word);

        return response;
    }


    @Override
    @CacheEvict(value = "FILTER_LIST",allEntries = true)
    public WordResponse createWord(WordRequest request) {
        checkIfWordIsAlreadyExists(request.mayaWord());

        Word word = new Word();
        if(request.title()==null)
            word.setCreateBy("WS");
        else if(request.title().equals("BI")) word.setCreateBy("BI");
        word.setMayaWord(request.mayaWord());
        word.setMeaning(request.meaning());
        WordResponse response = ConverterResponse.convert(repository.save(word));

        return response;
    }

    @Override
    public WordResponse createMeaningRequest(WordMeaningRequest request) {
        checkIfWordIsAlreadyExists(request.mayaWord());

        Word word = new Word();
        if(request.title()==null)
            word.setCreateBy("WS");
        else if(request.title().equals("BI")) word.setCreateBy("BI");
        word.setMayaWord(request.mayaWord());
        word.setMeaning("?");
        return ConverterResponse.convert(repository.save(word));
    }


    @Override
    public WordResponse updateWordById(UUID id, WordRequest request) {
        Word word = getWordByIdInRepository(id);
        word.setMayaWord(request.mayaWord());
        word.setMeaning(request.meaning());
        word.setCreatedTime(LocalDateTime.now());
        WordResponse response = ConverterResponse.convert(repository.save(word));

        return response;
    }

    @Override
    public void deleteWordById(UUID id) {
        Word word = getWordByIdInRepository(id);
        repository.delete(word);
    }

    @Override
    @Cacheable(value = "FILTER_LIST")
    public List<WordResponse> getWordsBySearchText(String filter) {
        List<WordResponse> responseList = new ArrayList<>(repository.findAllByMayaWordAsc().stream().filter(word -> word.getMayaWord().toLowerCase()
                .startsWith(filter.toLowerCase())).map(ConverterResponse::convert).toList());
        responseList.removeIf(response -> response.meaning().equals("?"));
        return responseList;
    }

    @Override
    public void increaseWordSearchCount(String mayaWord) {
        Word wordByName = repository.findWordByMayaWord(mayaWord);
        if (wordByName.getSearchCount() == null) {
            wordByName.setSearchCount(0);
        }
        Integer searchCount = wordByName.getSearchCount();
        wordByName.setSearchCount(++searchCount);
        repository.save(wordByName);

    }

    private Word getWordByIdInRepository(UUID id) {

        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(GlobalConstants.WORD_NOT_FOUND));
    }
    private Word getWordByNameInRepository(String name) {
        Word word = repository.findWordByMayaWord(name);
        if (word==null) {
            throw new EntityNotFoundException(GlobalConstants.WORD_NOT_FOUND);
        }
        return word;
    }
    private void checkIfWordIsAlreadyExists(String word) {
        if (repository.existsByMayaWord(word)) {
            Word wordByMayaWord = repository.findWordByMayaWord(word);
            if(wordByMayaWord.getMeaning().equals("?")) {
                repository.deleteById(wordByMayaWord.getId());
                return;
            }
            throw new EntityAlreadyExistsException(GlobalConstants.WORD_ALREADY_EXISTS);
        }
    }
}
