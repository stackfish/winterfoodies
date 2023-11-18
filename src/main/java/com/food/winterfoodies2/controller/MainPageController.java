package com.food.winterfoodies2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.food.winterfoodies2.dto.ApiResponseSuccessDto;
import com.food.winterfoodies2.dto.StoreLocationDto;
import com.food.winterfoodies2.entity.Category;
import com.food.winterfoodies2.entity.Store;
import com.food.winterfoodies2.service.CategoryService;
import com.food.winterfoodies2.service.SnackService;
import java.util.List;

import com.food.winterfoodies2.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin({"*"})
@RestController
@RequestMapping({"/api/main"})
@AllArgsConstructor
public class MainPageController {
    private final CategoryService categoryService;
    private final SnackService snackService;
    private final StoreService storeService;

    @PostMapping("/stores")
    public ResponseEntity<?> saveNearbyStores(@RequestBody StoreLocationDto storeLocationDto) throws JsonProcessingException {
        storeService.saveNearbyStores(storeLocationDto.getLatitude(), storeLocationDto.getLongitude());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stores")
    public ResponseEntity<ApiResponseSuccessDto> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();// 28
        ApiResponseSuccessDto response = new ApiResponseSuccessDto(categories);// 29]
        return new ResponseEntity<>(response, HttpStatus.OK);// 30
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseSuccessDto> getCategory(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);// 36
        ApiResponseSuccessDto response = new ApiResponseSuccessDto(category);// 37
        return new ResponseEntity<>(response, HttpStatus.OK);// 38
    }

    @GetMapping("/snacks")
    public Mono<ResponseEntity<ApiResponseSuccessDto>> getNearbySnacks(@RequestParam double lat, @RequestParam double lon) {
        return snackService.getNearbySnacks(lat, lon).map((snacks) -> {
            ApiResponseSuccessDto response = new ApiResponseSuccessDto(snacks);
            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }
}
