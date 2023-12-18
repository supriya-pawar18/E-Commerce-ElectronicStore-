package com.electronicstore.electronicStoreApp.dto;

import com.electronicstore.electronicStoreApp.entites.Order;
import com.electronicstore.electronicStoreApp.entites.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orederItemId;

    private int quantity;

    private int totalPrice;

    private ProductDto product;

    private OrderDto order;

}
