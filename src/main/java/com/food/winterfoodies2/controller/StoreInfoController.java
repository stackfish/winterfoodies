package com.food.winterfoodies2.controller;

import com.food.winterfoodies2.dto.ApiResponseSuccessDto;
import com.food.winterfoodies2.dto.StoreInfo.*;
import com.food.winterfoodies2.security.JwtTokenProvider;
import com.food.winterfoodies2.service.StoreInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/api/store")
@AllArgsConstructor
public class StoreInfoController {

    private final StoreInfoService storeInfoService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/menu/{id}")
    public ResponseEntity<ApiResponseSuccessDto> getMenuItem(@RequestHeader(value="Authorization", required=false) String token, @PathVariable Long id) {
        Long userId = null;
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.split(" ")[1];
            JwtTokenProvider.UserClaims userClaims = jwtTokenProvider.getUserClaims(jwtToken);
            userId = userClaims.getUserId();
        }

        StoreInfoMenuApiResponseSuccessDto menuItems = storeInfoService.getMenuItems(userId, id);
        ApiResponseSuccessDto response = new ApiResponseSuccessDto(menuItems);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<ApiResponseSuccessDto> getStoreInfo(@RequestHeader(value="Authorization", required=false) String token, @PathVariable Long id) {
        Long userId = null;
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.split(" ")[1];
            JwtTokenProvider.UserClaims userClaims = jwtTokenProvider.getUserClaims(jwtToken);
            userId = userClaims.getUserId();
        }

        StoreInfoDto storeInfo = storeInfoService.getStoreInfo(id, userId);
        return ResponseEntity.ok(new ApiResponseSuccessDto(storeInfo));
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<StoreInfoReviewApiResponseSuccessDto> getReview(
            @RequestHeader(value="Authorization", required=false) String token,
            @PathVariable Long id) {
        Long userId = null;
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.split(" ")[1];
            JwtTokenProvider.UserClaims userClaims = jwtTokenProvider.getUserClaims(jwtToken);
            userId = userClaims.getUserId();
        }

        StoreInfoReviewApiResponseSuccessDto responseDto = storeInfoService.getStoreInfoWithReviews(id, userId);
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("/favorite")
    public ResponseEntity<ApiResponseSuccessDto> toggleFavorite(@RequestHeader(value="Authorization") String token, @RequestBody FavoriteDto favoriteDto) {
        String jwtToken = token.split(" ")[1];
        JwtTokenProvider.UserClaims userClaims = jwtTokenProvider.getUserClaims(jwtToken);
        Long userId = userClaims.getUserId();

        boolean isFavorite = storeInfoService.toggleFavorite(userId, favoriteDto.getStoreId());

        Map<String, Object> data = new HashMap<>();
        data.put("message", "Favorite status updated successfully");
        data.put("isFavorite", isFavorite);
        ApiResponseSuccessDto response = new ApiResponseSuccessDto(data);
        return ResponseEntity.ok(response);
    }
}
