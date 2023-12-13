package com.electronicstore.electronicStoreApp.repository;

import com.electronicstore.electronicStoreApp.ServiceI.CategoryService;
import com.electronicstore.electronicStoreApp.entites.Cart;
import com.electronicstore.electronicStoreApp.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Integer> {

}
