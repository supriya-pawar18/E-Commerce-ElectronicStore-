package com.electronicstore.electronicStoreApp.repository;

import com.electronicstore.electronicStoreApp.dto.UserDto;
import com.electronicstore.electronicStoreApp.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {


   // User findById(UserDto id);
    User findByUser(UserDto id);


    Optional<User> findUserByEmail(String email);

    //public List<User> findByNameContaining(String keyword);

    List<User> findByNameContaining(String keyword);


}
