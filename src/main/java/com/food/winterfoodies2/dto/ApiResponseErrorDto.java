package com.food.winterfoodies2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponseErrorDto {
    private String status = "error";
    private String message;

    public ApiResponseErrorDto(final String status, final String message) {
        this.status = status;
        this.message = message;// 11
    }
}
