package com.electronicstore.electronicStoreApp.repository;

import com.electronicstore.electronicStoreApp.entites.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order,String> {

}
