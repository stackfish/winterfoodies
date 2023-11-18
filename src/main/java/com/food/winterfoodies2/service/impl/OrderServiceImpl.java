package com.food.winterfoodies2.service.impl;

import com.food.winterfoodies2.dto.Cart.CartActionDto;
import com.food.winterfoodies2.dto.OrderRequestDto;
import com.food.winterfoodies2.entity.MenuItem;
import com.food.winterfoodies2.entity.OrderItem;
import com.food.winterfoodies2.entity.OrderList;
import com.food.winterfoodies2.repository.MenuItemRepository;
import com.food.winterfoodies2.repository.OrderListRepository;
import com.food.winterfoodies2.service.CartService;
import com.food.winterfoodies2.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderListRepository orderListRepository;
    private CartService cartService;
    private MenuItemRepository menuItemRepository;

    @Override
    @Transactional
    public OrderList createOrder(Long userId, OrderRequestDto orderRequestDto) {
        int totalPrice = 0;

        OrderList order = new OrderList();
        order.setUserId(userId);
        order.setOrderTime(LocalDateTime.now());

        List<OrderItem> items = new ArrayList<>();
        String storeName = null;
        Long storeId = null;

        List<CartActionDto> cartItems = cartService.getCarts(userId);

        if (orderRequestDto.getItems() == null || orderRequestDto.getItems().size() != cartItems.size()) {
            throw new RuntimeException("The number of items in the order does not match the number of items in the cart.");
        }

        for (CartActionDto cartItem : cartItems) {
            Long itemId =cartItem.getItemId(); // String을 Long으로 변환
            MenuItem menuItem = menuItemRepository.findById(itemId)
                    .orElseThrow(() -> new RuntimeException("Menu item not found"));
            if (storeName == null && menuItem.getStore() != null) {
                storeName = menuItem.getStore().getName();
                storeId = menuItem.getStore().getId();
            }


            // productId를 사용하여 OrderItemDto를 가져옵니다.
            OrderRequestDto.OrderItemDto matchedOrderItemDto = orderRequestDto.getItems().stream()
                    .filter(itemDto -> itemId.equals(itemDto.getProductId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Order item not found for productId: " + itemId));

            OrderItem orderItem = new OrderItem();
            orderItem.setItemName(menuItem.getMenuName());
            orderItem.setPrice(menuItem.getPrice());
            orderItem.setItemId(itemId);
            orderItem.setQuantity(matchedOrderItemDto.getQuantity());
            orderItem.setOrder(order);
            items.add(orderItem);

            totalPrice += (orderItem.getPrice() * orderItem.getQuantity());
        }

        order.setItems(items);
        order.setStoreName(storeName);
        order.setStoreId(storeId);
        order.setTotalPrice(totalPrice);
        // order.setTotalPrice(cart.getTotalPrice()); // Calculate total price

        // Empty the cart after the order is created
        cartService.emptyCart(userId);

        return orderListRepository.save(order);
    }
}