package com.electronicstore.electronicStoreApp.entites;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orederItemId;

    private int quantity;

    private int totalPrice;

    @OneToOne
    private Product product;

}
