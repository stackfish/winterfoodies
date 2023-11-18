package com.food.winterfoodies2.controller;

import com.food.winterfoodies2.dto.Cart.CartActionDto;
import com.food.winterfoodies2.dto.Cart.CartApiResponseSuccessDto;
import com.food.winterfoodies2.dto.OrderRequestDto;
import com.food.winterfoodies2.entity.OrderList;
import com.food.winterfoodies2.security.JwtTokenProvider;
import com.food.winterfoodies2.service.CartService;
import com.food.winterfoodies2.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    private final OrderService orderService;

    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/items")
    public ResponseEntity<?> addItem(@RequestHeader(value="Authorization") String token, @RequestBody CartActionDto cartActionDto) {
        String jwtToken = token.split(" ")[1];
        JwtTokenProvider.UserClaims userClaims = jwtTokenProvider.getUserClaims(jwtToken);
        Long userId = userClaims.getUserId();

        cartActionDto.setUserId(userId);
        // Ignore quantity field if present in the request body
        cartActionDto.setQuantity(0);

        cartService.addItem(cartActionDto);

        Map<String, Boolean> response = new HashMap<>();
        response.put("success", true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @DeleteMapping("/items")
    public ResponseEntity<?> removeItem(@RequestHeader(value="Authorization") String token, @RequestBody CartActionDto action) {
        String jwtToken = token.split(" ")[1];
        JwtTokenProvider.UserClaims userClaims = jwtTokenProvider.getUserClaims(jwtToken);
        Long userId = userClaims.getUserId();

        action.setUserId(userId);

        cartService.removeItem(userId, action.getItemId());
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", true);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/items")
    public ResponseEntity<?> getCart(@RequestHeader(value="Authorization") String token) {
        String jwtToken = token.split(" ")[1];
        JwtTokenProvider.UserClaims userClaims = jwtTokenProvider.getUserClaims(jwtToken);
        Long userId = userClaims.getUserId();

        Optional<CartApiResponseSuccessDto> cartOptional = cartService.getCart(userId);
        if (!cartOptional.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "장바구니가 비었습니다.");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.ok(cartOptional.get());
    }

    @PostMapping("/order")
    public ResponseEntity<OrderList> createOrder(@RequestHeader(value="Authorization") String token, @RequestBody OrderRequestDto orderRequestDto) {
        String jwtToken = token.split(" ")[1];
        JwtTokenProvider.UserClaims userClaims = jwtTokenProvider.getUserClaims(jwtToken);
        Long userId = userClaims.getUserId();

        OrderList order = orderService.createOrder(userId, orderRequestDto);
        return ResponseEntity.ok(order);
    }

}

