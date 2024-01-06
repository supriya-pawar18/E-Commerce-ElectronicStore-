package com.electronicstore.electronicStoreApp.repository;

import com.electronicstore.electronicStoreApp.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
