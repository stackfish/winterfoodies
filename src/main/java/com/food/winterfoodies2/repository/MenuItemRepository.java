package com.food.winterfoodies2.repository;

import com.food.winterfoodies2.entity.MenuItem;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByStoreId(Long storeId);

    @NotNull
    Optional<MenuItem> findById(@NotNull Long itemId);

    Optional<MenuItem> findMenuItemById(Long itemId);

}
