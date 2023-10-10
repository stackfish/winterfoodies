package com.food.winterfoodies2.controller;

import com.food.winterfoodies2.dto.ApiResponseSuccessDto;
import com.food.winterfoodies2.entity.Category;
import com.food.winterfoodies2.service.CategoryService;
import com.food.winterfoodies2.service.SnackService;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping({"/api/main"})
@AllArgsConstructor
public class MainPageController {
    private final CategoryService categoryService;
    private final SnackService snackService;

    @GetMapping
    public ResponseEntity<ApiResponseSuccessDto> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();// 28
        ApiResponseSuccessDto response = new ApiResponseSuccessDto(categories);// 29
        return new ResponseEntity<>(response, HttpStatus.OK);// 30
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<ApiResponseSuccessDto> getCategory(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);// 36
        ApiResponseSuccessDto response = new ApiResponseSuccessDto(category);// 37
        return new ResponseEntity<>(response, HttpStatus.OK);// 38
    }

    @GetMapping({"/snacks"})
    public Mono<ResponseEntity<ApiResponseSuccessDto>> getNearbySnacks(@RequestParam double lat, @RequestParam double lon) {
        return snackService.getNearbySnacks(lat, lon).map((snacks) -> {// 43 44
            ApiResponseSuccessDto response = new ApiResponseSuccessDto(snacks);// 45
            return new ResponseEntity<>(response, HttpStatus.OK);// 46
        });
    }

}
