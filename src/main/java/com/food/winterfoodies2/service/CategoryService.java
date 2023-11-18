package com.food.winterfoodies2.service;

import com.food.winterfoodies2.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(Long id);

}
