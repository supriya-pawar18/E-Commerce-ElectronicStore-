package com.electronicstore.electronicStoreApp.entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String orderId;

    //Pending,Dispatched/,Delivered
    private String orderStatus;
    //Not-Paid,Paid
    //boolean
    private String paymentStatus;

    private  int orderAmount;

    private String billingAddress;

    private  String billingPhone;

    private String billingName;

    private Date orderedDate;

    private Date deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItems=new ArrayList<>();

}
