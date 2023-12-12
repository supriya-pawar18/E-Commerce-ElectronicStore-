package com.electronicstore.electronicStoreApp.entites;

import jakarta.persistence.*;
import lombok.*;

import javax.xml.crypto.Data;
import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    private String productId;

    private String title;

    private String description;

    private int price;

    private int discountedPrice;

    private int quantity;

    private Date addedDate;

    private boolean live;

    private  boolean stock;

    private String productImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_ID")
    private Category category;


}
