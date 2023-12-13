package com.electronicstore.electronicStoreApp.repository;

import com.electronicstore.electronicStoreApp.entites.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartIemRepo extends JpaRepository<CartItem,Integer> {
}
