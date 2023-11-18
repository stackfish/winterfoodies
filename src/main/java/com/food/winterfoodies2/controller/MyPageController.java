package com.food.winterfoodies2.controller;

import com.food.winterfoodies2.dto.ApiResponse;
import com.food.winterfoodies2.dto.ApiResponseSuccessDto;
import com.food.winterfoodies2.dto.MyPage.FavoriteStoreDto;
import com.food.winterfoodies2.dto.MyPage.OrderListDto;
import com.food.winterfoodies2.dto.MyPage.ReviewRequestDto;
import com.food.winterfoodies2.dto.ReviewResponseDto;
import com.food.winterfoodies2.dto.account.PasswordChangeRequest;
import com.food.winterfoodies2.dto.account.UserResponseDto;
import com.food.winterfoodies2.entity.Review;
import com.food.winterfoodies2.entity.Store;
import com.food.winterfoodies2.security.JwtTokenProvider;
import com.food.winterfoodies2.service.MyPageService;
import com.food.winterfoodies2.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.rmi.ServerException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

@CrossOrigin({"*"})
@RestController
@RequestMapping({"/api/mypage"})
@AllArgsConstructor
public class MyPageController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final MyPageService myPageService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(@RequestHeader("Authorization") String token) {
        // Remove "Bearer " from the token
        token = token.replace("Bearer ", "");

        // Extract the username from the token
        String username = jwtTokenProvider.getUserClaims(token).getUsername();

        // Get the user from the database
        UserResponseDto userResponseDto = userService.getUserByUsername(username);

        // Return the user's information
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest request) {
        userService.changePassword(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/favorite")
    public ResponseEntity<ApiResponseSuccessDto> getFavoriteStores(@RequestHeader(value="Authorization") String token) {
        String jwtToken = token.split(" ")[1];
        JwtTokenProvider.UserClaims userClaims = jwtTokenProvider.getUserClaims(jwtToken);
        Long userId = userClaims.getUserId();

        Set<FavoriteStoreDto> favoriteStores = myPageService.getFavoriteStores(userId);
        ApiResponseSuccessDto response = new ApiResponseSuccessDto(favoriteStores);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponseSuccessDto> getOrderLists(@RequestHeader(value="Authorization") String token) {
        String jwtToken = token.split(" ")[1];
        JwtTokenProvider.UserClaims userClaims = jwtTokenProvider.getUserClaims(jwtToken);
        Long userId = userClaims.getUserId();

        List<OrderListDto> orderLists = myPageService.getOrderLists(userId);
        ApiResponseSuccessDto response = new ApiResponseSuccessDto(orderLists);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/review/{orderId}")
    public ResponseEntity<ApiResponse> createReview(@RequestHeader(value="Authorization") String token,
                                                    @PathVariable Long orderId,
                                                    @ModelAttribute ReviewRequestDto reviewRequest) {
        try {
            String jwtToken = token.split(" ")[1];
            JwtTokenProvider.UserClaims userClaims = jwtTokenProvider.getUserClaims(jwtToken);
            Long userId = userClaims.getUserId();
            Review review = myPageService.createReview(userId, orderId, reviewRequest);
            myPageService.addReviewImages(review.getId(), reviewRequest.getImage());

            ApiResponse response = new ApiResponse("success", "리뷰작성완료");
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/review")
    public ResponseEntity<ReviewResponseDto> getUserReviews(@RequestHeader("Authorization") String token) {
        String jwtToken = token.replace("Bearer ", "");
        Long userId = jwtTokenProvider.getUserClaims(jwtToken).getUserId();
        ReviewResponseDto reviews = myPageService.getUserReviews(userId);
        return ResponseEntity.ok(reviews);
    }
}
