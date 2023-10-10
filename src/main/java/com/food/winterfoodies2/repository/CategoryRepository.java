package com.food.winterfoodies2.repository;

import com.food.winterfoodies2.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
