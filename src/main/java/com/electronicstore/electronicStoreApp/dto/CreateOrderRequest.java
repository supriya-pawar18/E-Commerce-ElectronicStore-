package com.electronicstore.electronicStoreApp.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CreateOrderRequest {

    private String cartId;
    private String id;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    private UserDto user;
    private List<OrderItemDto> orderItems=new ArrayList<>();
}
