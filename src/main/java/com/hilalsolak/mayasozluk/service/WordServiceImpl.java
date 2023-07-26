package com.hilalsolak.mayasozluk.service;

import com.hilalsolak.mayasozluk.converters.ConverterResponse;
import com.hilalsolak.mayasozluk.model.dto.WordRequest;
import com.hilalsolak.mayasozluk.model.dto.WordResponse;
import com.hilalsolak.mayasozluk.model.entities.Word;
import com.hilalsolak.mayasozluk.model.repository.WordRepository;
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
        checkIfWordIsAlreadyExists(request.word());
        Word word = new Word();
        word.setWord(request.word());
        word.setMeaning(request.meaning());
        WordResponse response = ConverterResponse.convert(repository.save(word));
        return response;
    }



    @Override
    public WordResponse updateWordById(UUID id, WordRequest request) {
        Word word = getWordByIdInRepository(id);
        word.setWord(request.word());
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
        List<WordResponse> responseList = repository.findAllByWordAsc().stream().filter(word -> word.getWord()
                .startsWith(filter)).map(ConverterResponse::convert).toList();

        return responseList;
    }

    @Override
    public void increaseWordSearchCount(String wordName) {
        Word wordByName = repository.findWordByWord(wordName);
        if(wordByName.getSearchCount() == null) {
            wordByName.setSearchCount(0);
        }
        Integer searchCount = wordByName.getSearchCount();
        wordByName.setSearchCount(++searchCount);
        repository.save(wordByName);

    }
    private Word getWordByIdInRepository(UUID id) {

        return repository.findById(id).orElseThrow(()->new RuntimeException("kategori mevcut degil"));
    }
    private void checkIfWordIsAlreadyExists(String word) {
        if(repository.existsByWord(word)){
            throw new RuntimeException("Entity Already Exists");
        }
    }
}
