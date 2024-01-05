package com.electronicstore.electronicStoreApp.dto;

import com.electronicstore.electronicStoreApp.entites.Order;
import com.electronicstore.electronicStoreApp.entites.Product;
import javax.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderItemDto {

    private int orederItemId;

    private int quantity;

    private int totalPrice;

    private ProductDto product;

    private OrderDto order;

}
