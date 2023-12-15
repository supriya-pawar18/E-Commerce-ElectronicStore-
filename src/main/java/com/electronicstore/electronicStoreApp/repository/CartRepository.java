package com.electronicstore.electronicStoreApp.repository;

import com.electronicstore.electronicStoreApp.ServiceI.CategoryService;
import com.electronicstore.electronicStoreApp.entites.Cart;
import com.electronicstore.electronicStoreApp.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,String> {

    Optional<Cart> findByUser(User user);

}
