package com.food.winterfoodies2.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.food.winterfoodies2.dto.MyPage.FavoriteStoreDto;
import com.food.winterfoodies2.dto.MyPage.OrderListDto;
import com.food.winterfoodies2.dto.MyPage.ReviewRequestDto;
import com.food.winterfoodies2.dto.ReviewResponseDto;
import com.food.winterfoodies2.entity.*;
import com.food.winterfoodies2.repository.*;
import com.food.winterfoodies2.service.MyPageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final UserRepository userRepository;
    private final OrderListRepository orderListRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final AmazonS3 amazonS3;

    @Override
    public Set<FavoriteStoreDto> getFavoriteStores(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFavoriteStores().stream()
                .map(this::convertToDto)
                .collect(Collectors.toSet());
    }

    private FavoriteStoreDto convertToDto(Store store) {
        return new FavoriteStoreDto(
                store.getId(),
                store.getName(),
                store.getAddress(),
                store.getDistance(),
                store.getRating(),
                store.getImageUrl()
        );
    }

    public List<OrderListDto> getOrderLists(Long userId) {
        List<OrderList> orderLists = orderListRepository.findByUserId(userId);
        return orderLists.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private OrderListDto convertToDto(OrderList orderList) {
        List<OrderListDto.OrderItemDto> items = orderList.getItems().stream()
                .map(item -> new OrderListDto.OrderItemDto(item.getItemId(), item.getItemName(), item.getQuantity()))
                .collect(Collectors.toList());

        Store store = storeRepository.findById(orderList.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        boolean hasReview = reviewRepository.existsByUserIdAndOrderId(orderList.getUserId(), orderList.getId());

        return new OrderListDto(
                orderList.getId(),
                orderList.getStoreId(),
                orderList.getStoreName(),
                store.getImageUrl(),
                store.getRating(),
                orderList.getOrderTime(),
                orderList.getTotalPrice(),
                items,
                hasReview
        );
    }

    @Override
    public Review createReview(Long userId, Long orderId, ReviewRequestDto reviewRequest) throws IOException {
        // Retrieve the user and order from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        OrderList order = orderListRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Retrieve the store associated with the order
        Store store = storeRepository.findById(order.getStoreId())
                .orElseThrow(() -> new RuntimeException("Store not found"));

        // Create a new Review instance and set its properties
        Review review = new Review();
        review.setUser(user);
        review.setOrder(order);
        review.setStore(store); // Set the store associated with the review
        review.setRating(reviewRequest.getRating());
        review.setContent(reviewRequest.getContent());

        // Save the Review instance to the database
        Review savedReview = reviewRepository.save(review);

        // Check if an image is provided and save it
        MultipartFile image = reviewRequest.getImage();
        if (image != null) {
            // Save the image and get the URL
            String imageUrl = saveImage(image);

            // Create a new ReviewImage instance and set its properties
            ReviewImage reviewImage = new ReviewImage();
            reviewImage.setImageUrl(imageUrl);
            reviewImage.setReview(savedReview); // Associate the ReviewImage with the saved Review

            // Save the ReviewImage instance to the database
            reviewImageRepository.save(reviewImage);

            // Update the imageUrl of the saved Review instance if necessary
            savedReview.setImageUrl(imageUrl);
            // Save the Review instance again if it's updated
            reviewRepository.save(savedReview);
        }

        // Return the saved Review instance
        return savedReview;
    }


    @Override
    public void addReviewImages(Long reviewId, MultipartFile image) throws IOException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));

            String imageUrl = saveImage(image);
            ReviewImage reviewImage = new ReviewImage();
            reviewImage.setImageUrl(imageUrl);
            reviewImage.setReview(review);


        reviewRepository.save(review);
    }

    private String saveImage(MultipartFile image) throws IOException {
        String bucketName = "imagestorage";
        String objectName = "picture/" + UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

        // 이미지를 임시 파일로 저장
        Path tempFile = Files.createTempFile(null, null);
        try (FileOutputStream out = new FileOutputStream(tempFile.toFile())) {
            out.write(image.getBytes());
        }

        // Linode Object Storage에 업로드
        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, objectName, tempFile.toFile()));
        } catch (Exception e) {
            e.printStackTrace();
            return "Error in file upload";
        } finally {
            Files.delete(tempFile); // 임시 파일 삭제
        }

        // 임시 파일 삭제 후 URL 반환
        return amazonS3.getUrl(bucketName, objectName).toString();
    }

    @Override
    public ReviewResponseDto getUserReviews(Long userId) {
        List<Review> reviews = reviewRepository.findByUserIdWithStore(userId); // Make sure this method fetches the store information as well
        List<ReviewResponseDto.ReviewDetails> reviewDetails = reviews.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new ReviewResponseDto(reviewDetails);
    }

    private ReviewResponseDto.ReviewDetails convertToDto(Review review) {

        String storeName = review.getStore() != null ? review.getStore().getName() : null; // Assuming Store entity has a getName() method
        LocalDateTime orderTime = review.getOrder() != null ? review.getOrder().getOrderTime() : null;
        List<String> orderedItems = review.getOrder() != null ? review.getOrder().getItems().stream()
                .map(item -> item.getItemName() + " x " + item.getQuantity())
                .collect(Collectors.toList()) : Collections.emptyList();
        String imageUrl = review.getImageUrl(); // Assuming this is the URL of the image associated with the review

        return new ReviewResponseDto.ReviewDetails(
                review.getId(),
                storeName,
                orderTime,
                review.getRating(),
                orderedItems,
                review.getContent(),
                imageUrl
        );
    }

}