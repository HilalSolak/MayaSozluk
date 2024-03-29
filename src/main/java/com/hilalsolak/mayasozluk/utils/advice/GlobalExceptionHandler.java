package com.hilalsolak.mayasozluk.utils.advice;

import com.hilalsolak.mayasozluk.model.dto.responses.ExceptionResponse;
import com.hilalsolak.mayasozluk.utils.advice.exceptions.EntityAlreadyExistsException;
import com.hilalsolak.mayasozluk.utils.advice.exceptions.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ExceptionResponse handleEntityNotFoundException(EntityNotFoundException exception, HttpServletRequest request ){
        return new ExceptionResponse(HttpStatus.BAD_REQUEST,request.getServletPath(), LocalDateTime.now(), exception.getMessage());
    }
    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ExceptionResponse handleEntityAlreadyExistsException(EntityAlreadyExistsException exception, HttpServletRequest request ){
        return new ExceptionResponse(HttpStatus.BAD_REQUEST,request.getServletPath(), LocalDateTime.now(), exception.getMessage());
    }


}
