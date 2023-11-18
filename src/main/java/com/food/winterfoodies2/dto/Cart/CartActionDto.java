package com.food.winterfoodies2.dto.Cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.annotation.processing.Generated;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartActionDto implements Serializable{
    private Long userId;
    private Long itemId;
    private String itemName;
    private int price;
    private int quantity; // This field will be removed from the request body
}
