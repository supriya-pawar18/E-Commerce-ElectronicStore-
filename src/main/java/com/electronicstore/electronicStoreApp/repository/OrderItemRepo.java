package com.electronicstore.electronicStoreApp.repository;

import com.electronicstore.electronicStoreApp.entites.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem,Integer> {
}
