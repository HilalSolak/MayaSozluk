package com.hilalsolak.mayasozluk.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_WORDS")
public class Word extends BaseModel {
    private String word;

    private String meaning;

    private Integer searchCount;
    private String createBy;

    public Word() {
    }

    public Word(String word, String meaning, Integer searchCount, String createBy) {
        this.word = word;
        this.meaning = meaning;
        this.searchCount = searchCount;
        this.createBy = createBy;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public Integer getSearchCount() {
        return searchCount;
    }

    public void setSearchCount(Integer searchCount) {
        this.searchCount = searchCount;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
