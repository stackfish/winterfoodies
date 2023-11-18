package com.food.winterfoodies2.service;

import com.food.winterfoodies2.dto.MyPage.FavoriteStoreDto;
import com.food.winterfoodies2.dto.MyPage.OrderListDto;
import com.food.winterfoodies2.dto.MyPage.ReviewRequestDto;
import com.food.winterfoodies2.dto.ReviewResponseDto;
import com.food.winterfoodies2.entity.Review;
import com.food.winterfoodies2.entity.Store;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

public interface MyPageService {
    Set<FavoriteStoreDto> getFavoriteStores(Long userId);

    List<OrderListDto> getOrderLists(Long userId);

    Review createReview(Long userId, Long orderId, ReviewRequestDto reviewRequest) throws IOException;

    void addReviewImages(Long reviewId, MultipartFile image) throws IOException;

    ReviewResponseDto getUserReviews(Long userId);
}
