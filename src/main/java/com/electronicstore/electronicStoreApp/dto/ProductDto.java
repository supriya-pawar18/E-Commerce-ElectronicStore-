package com.electronicstore.electronicStoreApp.dto;

import jakarta.persistence.Column;

import javax.xml.crypto.Data;
import java.util.Date;

public class ProductDto {
    private String productId;

    private String title;

    @Column(length = 1000)
    private String description;

    private int price;

    private int discountedPrice;

    private int quantity;

    private Date addedDate;

    private boolean live;

    private  boolean stock;
}
