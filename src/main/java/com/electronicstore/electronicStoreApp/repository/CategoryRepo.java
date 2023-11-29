package com.electronicstore.electronicStoreApp.repository;

import com.electronicstore.electronicStoreApp.entites.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,String> {
}
