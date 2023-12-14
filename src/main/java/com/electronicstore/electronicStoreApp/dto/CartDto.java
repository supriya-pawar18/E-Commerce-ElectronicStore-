package com.electronicstore.electronicStoreApp.dto;

import com.electronicstore.electronicStoreApp.entites.User;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private String cartId;
    private Date createdAt;
    private User user;
}
