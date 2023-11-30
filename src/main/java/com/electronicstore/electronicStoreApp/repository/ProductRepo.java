package com.electronicstore.electronicStoreApp.repository;

import com.electronicstore.electronicStoreApp.entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,String> {

    //search
    List<Product> findByTitleContaining(String subTitle);
    List<Product> findByLiveTrue();

}
