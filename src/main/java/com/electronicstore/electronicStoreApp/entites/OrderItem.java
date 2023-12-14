package com.electronicstore.electronicStoreApp.entites;

import jakarta.persistence.*;
import lombok.*;
import org.modelmapper.internal.bytebuddy.dynamic.loading.InjectionClassLoader;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItem;

    private int quantity;

    private int totalPrice;

    @OneToOne
    private Product product;
    @ManyToOne
    private Order order;
}
