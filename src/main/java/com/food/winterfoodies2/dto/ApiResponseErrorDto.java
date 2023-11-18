package com.food.winterfoodies2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseErrorDto implements ApiResponseDto {
    private String status = "error";
    private String statusCode;
    private String message;

    public ApiResponseErrorDto(String message, String statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
