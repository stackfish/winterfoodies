package com.food.winterfoodies2.service.impl;

import com.food.winterfoodies2.entity.Category;
import com.food.winterfoodies2.exception.ResourceNotFoundException;
import com.food.winterfoodies2.repository.CategoryRepository;
import com.food.winterfoodies2.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category Not Found"));
    }
}
