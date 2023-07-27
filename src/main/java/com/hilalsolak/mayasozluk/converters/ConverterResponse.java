package com.hilalsolak.mayasozluk.converters;

import com.hilalsolak.mayasozluk.model.dto.responses.LoggerResponse;
import com.hilalsolak.mayasozluk.model.dto.responses.WordResponse;
import com.hilalsolak.mayasozluk.model.entities.Logger;
import com.hilalsolak.mayasozluk.model.entities.Word;

public class ConverterResponse {

    public static WordResponse convert(Word from){
        return new WordResponse(from.getId(),
                from.getMayaWord(),
                from.getMeaning());
    }
    public static LoggerResponse convert(Logger from)  {
        return new LoggerResponse(from.getId(),
                from.getClientId(),
                from.getActivityType(),
                from.getCreatedTime());}
}
