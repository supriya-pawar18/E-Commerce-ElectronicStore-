package com.electronicstore.electronicStoreApp.dto;

import com.electronicstore.electronicStoreApp.entites.OrderItem;
import com.electronicstore.electronicStoreApp.entites.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    @Id
    private  String orderId;

    private String orderStatus="PENDING";

    private String paymentStatus="NOTPAID";

    private int orderAmount;

    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderDate=new Date();

    private Date deliveredDate;
    //user
    private UserDto user;

    private List<OrderItemDto> orderItems=new ArrayList<>();
}
