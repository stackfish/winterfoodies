package com.food.winterfoodies2.exception;

import com.food.winterfoodies2.dto.ApiResponseErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoAPIException.class)
    public ResponseEntity<ApiResponseErrorDto> handleTodoAPIException(TodoAPIException ex) {
        HttpStatus status = ex.getStatus();
        ApiResponseErrorDto errorResponse = new ApiResponseErrorDto(ex.getMessage(), status.toString());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponseErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiResponseErrorDto errorResponse = new ApiResponseErrorDto(ex.getMessage(), status.toString());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponseErrorDto> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiResponseErrorDto errorResponse = new ApiResponseErrorDto(ex.getMessage(), status.toString());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseErrorDto> handleResourceNotFoundException(ResourceNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiResponseErrorDto errorResponse = new ApiResponseErrorDto(ex.getMessage(), status.toString());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


}