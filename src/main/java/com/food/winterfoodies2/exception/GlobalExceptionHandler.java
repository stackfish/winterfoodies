package com.food.winterfoodies2.exception;

import com.food.winterfoodies2.dto.ApiResponseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoAPIException.class)
    public ResponseEntity<ApiResponseErrorDto> handleTodoAPIException(TodoAPIException exception,
                                                                      WebRequest webRequest){

        ApiResponseErrorDto errorResponse = new ApiResponseErrorDto(
                "error",
                exception.getMessage()
        );

        return new ResponseEntity<>(errorResponse, exception.getStatus());
    }
}