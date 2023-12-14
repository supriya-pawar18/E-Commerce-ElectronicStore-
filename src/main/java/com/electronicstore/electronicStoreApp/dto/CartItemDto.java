package com.electronicstore.electronicStoreApp.dto;

import com.electronicstore.electronicStoreApp.entites.Product;
import jakarta.persistence.OneToOne;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

    private int cartItemId;
    private ProductDto product;
    private int quantity;
    private int totalPrice;

}
