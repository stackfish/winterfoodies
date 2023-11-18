package com.food.winterfoodies2.dto.MyPage;

import com.food.winterfoodies2.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {

    private Double rating;
    private String content;
    private MultipartFile image;
    private String storeId;
}