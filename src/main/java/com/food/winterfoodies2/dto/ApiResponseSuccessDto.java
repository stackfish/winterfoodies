package com.food.winterfoodies2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseSuccessDto implements ApiResponseDto {
    private String status = "success";
    private Object data;

    public ApiResponseSuccessDto(Object data) {
        this.data = data;
    }
}