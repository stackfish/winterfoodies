package com.food.winterfoodies2.repository;

import com.food.winterfoodies2.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
