package com.electronicstore.electronicStoreApp.dto;

import com.electronicstore.electronicStoreApp.entites.CartItem;
import com.electronicstore.electronicStoreApp.entites.User;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private String cartId;
    private Date createdAt;
    private UserDto user;

    private List<CartItemDto> items = new ArrayList<>();

}
