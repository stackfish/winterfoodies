package com.food.winterfoodies2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
    private List<ReviewDetails> reviews;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewDetails {
        private Long id;
        private String storeName;
        private LocalDateTime orderTime;
        private Double rating;
        private List<String> orderedItems;
        private String content;
        private String imageUrl;
    }
}
