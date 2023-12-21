package com.electronicstore.electronicStoreApp.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class AddItemToCartRequest {

    private String productId;

    private int quantity;

}
