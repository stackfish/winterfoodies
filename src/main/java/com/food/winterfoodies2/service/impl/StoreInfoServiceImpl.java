package com.food.winterfoodies2.service.impl;

import com.food.winterfoodies2.dto.StoreInfo.*;
import com.food.winterfoodies2.entity.*;
import com.food.winterfoodies2.exception.ResourceNotFoundException;
import com.food.winterfoodies2.repository.*;
import com.food.winterfoodies2.security.JwtTokenProvider;
import com.food.winterfoodies2.service.StoreInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoreInfoServiceImpl implements StoreInfoService {

    private final MenuItemRepository menuItemRepository;
    private final ReviewRepository reviewRepository;
    private final StoreInfoRepository storeInfoRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public StoreInfoMenuApiResponseSuccessDto getMenuItems(Long userId, Long storeId) {
        List<MenuItemDto> menuItems = menuItemRepository.findByStoreId(storeId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        Store storeInfo = storeRepository.findById(storeId).orElse(null);
        User user = (userId != null) ? userRepository.findById(userId).orElse(null) : null;

        StoreInfoMenuApiResponseSuccessDto response = new StoreInfoMenuApiResponseSuccessDto();
        if (storeInfo != null) {
            response.setStoreName(storeInfo.getName());
            response.setRating(String.valueOf(storeInfo.getRating()));
            if (user != null) {
                response.setFavorites(user.getFavoriteStores().contains(storeInfo));
            } else {
                response.setFavorites(false);
            }
        }
        response.setMenu(menuItems);
        assert storeInfo != null;
        response.setImageUrl(storeInfo.getImageUrl());
        return response;
    }

    @Override
    public StoreInfoDto getStoreInfo(Long storeId, Long userId) {
        StoreInfo storeInfo = storeInfoRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeId));
        User user = (userId != null) ? userRepository.findById(userId).orElse(null) : null;

        StoreInfoDto storeInfoDto = convertToDto(storeInfo);
        if (user != null) {
            storeInfoDto.setFavorite(user.getFavoriteStores().contains(storeInfo));
        } else {
            storeInfoDto.setFavorite(false);
        }

        return storeInfoDto;
    }


    public List<ReviewDto> getReviews(Long storeId) {
        List<Review> reviews = reviewRepository.findByStoreId(storeId);
        return reviews.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public StoreInfoReviewApiResponseSuccessDto getStoreInfoWithReviews(Long storeId, Long userId) {
        List<ReviewDto> reviewDtos = getReviews(storeId);
        boolean isFavorite = false;
        if (userId != null) {
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                Store store = storeRepository.findById(storeId).orElse(null);
                isFavorite = user.getFavoriteStores().contains(store);
            }
        }
        return new StoreInfoReviewApiResponseSuccessDto("success", reviewDtos, isFavorite);
    }

    private MenuItemDto convertToDto(MenuItem menuItem) {
        if (menuItem == null) {
            return null;
        }

        MenuItemDto dto = new MenuItemDto();
        dto.setFoodId(menuItem.getId());
        dto.setMenuName(menuItem.getMenuName());
        dto.setPrice(menuItem.getPrice());
        // 다른 필드들도 이와 같은 방식으로 설정
        return dto;
    }

    private StoreInfoDto convertToDto(StoreInfo storeInfo) {
        if (storeInfo == null) {
            return null;
        }

        StoreInfoDto dto = new StoreInfoDto();
        dto.setAddress(storeInfo.getAddress());
        dto.setOwnerComment(storeInfo.getOwnerComment());
        dto.setBusinessHours(storeInfo.getBusinessHours());
        dto.setCookingTime(storeInfo.getCookingTime());
        dto.setDescription(storeInfo.getDescription());
        // 다른 필드들도 이와 같은 방식으로 설정
        return dto;
    }

    private ReviewDto convertToDto(Review review) {
        if (review == null) {
            return null;
        }

        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setUserNickname(review.getUser().getUsername());
        dto.setRating(review.getRating());
        dto.setOrderedItem(review.getOrder().getItems());
        dto.setContent(review.getContent());
        dto.setImageUrl(review.getImageUrl());

        // 다른 필드들도 이와 같은 방식으로 설정
        return dto;
    }

    @Override
    public boolean toggleFavorite(Long userId, Long storeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new ResourceNotFoundException("Store not found"));

        // 사용자가 이미 해당 상점을 찜했는지 확인합니다.
        boolean isFavorite = user.getFavoriteStores().contains(store);

        // 사용자가 이미 찜했다면 찜 목록에서 제거하고, 아니라면 찜 목록에 추가합니다.
        if (isFavorite) {
            user.getFavoriteStores().remove(store);
        } else {
            user.getFavoriteStores().add(store);
        }

        // 변경된 사용자 정보를 저장합니다.
        userRepository.save(user);

        // 찜하기 상태를 반전하여 반환합니다.
        return !isFavorite;
    }
}

// convertToDto methods for each entity...
