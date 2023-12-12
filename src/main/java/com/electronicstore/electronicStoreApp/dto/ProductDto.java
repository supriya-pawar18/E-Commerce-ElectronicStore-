package com.electronicstore.electronicStoreApp.dto;

import jakarta.persistence.Column;
import lombok.*;

import javax.xml.crypto.Data;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDto {

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

    private CategoryDto category;

}
