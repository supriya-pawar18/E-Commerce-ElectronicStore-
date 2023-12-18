package com.electronicstore.electronicStoreApp.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "orders")
public class Order {

      @Id
      private  String orderId;
      //pending,Delivered
      private String orderStatus;
      //Not Paid,Paid
      private String paymentStatus;

      private int orderAmount;

      private String billingAddress;

      private String billingPhone;

      private String billingName;

      private Date orderDate;

      private Date deliveredDate;

      //user
       @ManyToOne(fetch = FetchType.EAGER)
       private User user;

       @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
       private List<OrderItem> orderItems=new ArrayList<>();


}
