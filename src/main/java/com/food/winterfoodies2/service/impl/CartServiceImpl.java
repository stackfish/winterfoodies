package com.food.winterfoodies2.service.impl;

import com.food.winterfoodies2.dto.Cart.CartActionDto;
import com.food.winterfoodies2.dto.Cart.CartApiResponseSuccessDto;
import com.food.winterfoodies2.entity.MenuItem;
import com.food.winterfoodies2.entity.Store;
import com.food.winterfoodies2.repository.MenuItemRepository;
import com.food.winterfoodies2.repository.StoreRepository;
import com.food.winterfoodies2.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final RedisTemplate<String, CartActionDto> redisTemplate;
    private final MenuItemRepository menuItemRepository;
    private final StoreRepository storeRepository;


    @Override
    @Transactional
    public void addItem(CartActionDto action) {
        MenuItem menuItem = menuItemRepository.findById(action.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // 사용자의 장바구니에 담긴 상품이 있는지 확인
        Map<Object, Object> entries = redisTemplate.opsForHash().entries("cart:" + action.getUserId().toString());

        if (!entries.isEmpty()) {
            // 장바구니에 담긴 상품이 있다면, 그 상품의 상점이 현재 추가하려는 상품의 상점과 같은지 확인
            for (Object item : entries.values()) {
                CartActionDto cartItem = (CartActionDto) item;
                MenuItem cartMenuItem = menuItemRepository.findMenuItemById(cartItem.getItemId())
                        .orElseThrow(() -> new RuntimeException("Item not found"));
                if (!cartMenuItem.getStore().equals(menuItem.getStore())) {
                    // If the store is different, throw an exception to alert the front end
                    redisTemplate.delete("cart:" + action.getUserId().toString());
                } else if (cartMenuItem.getId().equals(action.getItemId())) {
                    // If you're trying to add an item that's already in the cart, set its quantity to 1
                    action.setQuantity(1);
                    break;
                }
            }
        }

        // 상품을 장바구니에 추가
        action.setItemName(menuItem.getMenuName());
        action.setPrice(menuItem.getPrice());
        redisTemplate.opsForHash().put("cart:" + action.getUserId().toString(), action.getItemId(), action);
    }



    @Override
    public void removeItem(Long userId, Long itemId) {
        redisTemplate.opsForHash().delete("cart:" + userId.toString(), itemId);
    }


    @Override
    public Optional<CartApiResponseSuccessDto> getCart(Long userId) {
        Map<Object, Object> rawMap = redisTemplate.opsForHash().entries("cart:" + userId);
        if (rawMap.isEmpty()) {
            return Optional.empty();
        }
        List<CartActionDto> cartItems = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : rawMap.entrySet()) {
            CartActionDto cartItem = (CartActionDto) entry.getValue();
            cartItem.setQuantity(1); // set quantity to 1
            cartItems.add(cartItem);
        }

        Optional<MenuItem> optional = menuItemRepository.findById(cartItems.get(0).getItemId());
        if (optional.isEmpty()) {
            throw new RuntimeException("MenuItem not found");
        }
        MenuItem menuItem = optional.get();

        Store storeInfo = storeRepository.findById(menuItem.getStore().getId()).orElse(null);

        assert storeInfo != null;

        // Set the itemCount when creating the response object
        CartApiResponseSuccessDto response = new CartApiResponseSuccessDto(
                "success",
                storeInfo.getImageUrl(),
                storeInfo.getCookingTime(),
                storeInfo.getName(),
                cartItems,
                cartItems.size() // Set the count of unique items here
        );
        return Optional.of(response);
    }




    @Override
    public void emptyCart(Long userId) {
        redisTemplate.delete("cart:" + userId);
    }

    @Override
    public List<CartActionDto> getCarts(Long userId) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries("cart:" + userId);
        List<CartActionDto> cartItems = new ArrayList<>();
        for (Object item : entries.values()) {
            cartItems.add((CartActionDto) item);
        }
        return cartItems;
    }
}

