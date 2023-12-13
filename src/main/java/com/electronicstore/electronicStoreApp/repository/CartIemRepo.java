package com.electronicstore.electronicStoreApp.repository;

import com.electronicstore.electronicStoreApp.entites.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartIemRepo extends JpaRepository<CartItem,Integer> {
}
