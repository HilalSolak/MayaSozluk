package com.hilalsolak.mayasozluk.converters;

import com.hilalsolak.mayasozluk.model.dto.WordResponse;
import com.hilalsolak.mayasozluk.model.entities.Word;

public class ConverterResponse {

    public static WordResponse convert(Word from){
        return new WordResponse(from.getId(),
                from.getWord(),
                from.getMeaning());
    }
}
