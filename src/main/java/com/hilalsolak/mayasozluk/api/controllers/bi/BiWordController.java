package com.hilalsolak.mayasozluk.api.controllers.bi;

import com.hilalsolak.mayasozluk.model.dto.requests.WordRequest;
import com.hilalsolak.mayasozluk.model.dto.responses.WordResponse;
import com.hilalsolak.mayasozluk.model.dto.requests.WordMeaningRequest;
import com.hilalsolak.mayasozluk.service.WordService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/words/screen")
public class BiWordController {
    private final WordService service;

    public BiWordController(WordService service) {
        this.service = service;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<WordResponse> getAllWords(){
        return service.getAllWords();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WordResponse getWordById(@PathVariable UUID id){
        return service.getWordById(id);
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public WordResponse createWord(@RequestBody WordRequest request){
        return service.createWord(request);
    }

    @PostMapping("/meaning")
    @ResponseStatus(HttpStatus.CREATED)
    public WordResponse createWordMeaning(@RequestBody WordMeaningRequest request){
        return service.createMeaningRequest(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public WordResponse updateWordById(@PathVariable UUID id, @RequestBody WordRequest request){
        return service.updateWordById(id,request);
    }
    @GetMapping("/filter")
    public List<WordResponse> getWordsBySearchText(@RequestParam("filter") String filter){
        return service.getWordsBySearchText(filter);
    }
    @PutMapping("/increase/{wordName}")
    @ResponseStatus(HttpStatus.OK)
    public void increaseWordSearchCount(@PathVariable String wordName){
        service.increaseWordSearchCount(wordName);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteWordById(@PathVariable UUID id){
        service.deleteWordById(id);
    }}
