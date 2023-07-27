package com.hilalsolak.mayasozluk.service.impl;

import com.hilalsolak.mayasozluk.converters.ConverterResponse;
import com.hilalsolak.mayasozluk.model.dto.requests.WordRequest;
import com.hilalsolak.mayasozluk.model.dto.responses.WordResponse;
import com.hilalsolak.mayasozluk.model.entities.Word;
import com.hilalsolak.mayasozluk.model.repository.WordRepository;
import com.hilalsolak.mayasozluk.service.WordService;
import com.hilalsolak.mayasozluk.utils.advice.exceptions.EntityAlreadyExistsException;
import com.hilalsolak.mayasozluk.utils.constants.GlobalConstants;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
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
    public WordResponse createWord(WordRequest request) {
        checkIfWordIsAlreadyExists(request.mayaWord());

        Word word = new Word();
        word.setMayaWord(request.mayaWord());
        word.setMeaning(request.meaning());
        WordResponse response = ConverterResponse.convert(repository.save(word));

        return response;
    }


    @Override
    public WordResponse updateWordById(UUID id, WordRequest request) {
        Word word = getWordByIdInRepository(id);
        word.setMayaWord(request.mayaWord());
        word.setMeaning(request.meaning());
        WordResponse response = ConverterResponse.convert(repository.save(word));

        return response;
    }

    @Override
    public void deleteWordById(UUID id) {
        Word word = getWordByIdInRepository(id);
        repository.delete(word);
    }

    @Override
    public List<WordResponse> getCategoriesBySearchText(String filter) {
        List<WordResponse> responseList = repository.findAllByMayaWordAsc().stream().filter(word -> word.getMayaWord()
                .startsWith(filter)).map(ConverterResponse::convert).toList();

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

    private void checkIfWordIsAlreadyExists(String word) {
        if (repository.existsByMayaWord(word)) {
            throw new EntityAlreadyExistsException(GlobalConstants.WORD_ALREADY_EXISTS);
        }
    }
}
