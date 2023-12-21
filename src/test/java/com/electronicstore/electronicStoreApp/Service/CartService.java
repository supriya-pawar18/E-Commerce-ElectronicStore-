package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.dto.CartDto;
import com.electronicstore.electronicStoreApp.dto.CategoryDto;
import com.electronicstore.electronicStoreApp.entites.Cart;
import com.electronicstore.electronicStoreApp.entites.User;
import com.electronicstore.electronicStoreApp.repository.CartRepository;
import com.electronicstore.electronicStoreApp.repository.ProductRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class CartService {

    @Autowired
    private CartService cartService;
    @MockBean
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepo productRepo;

    Cart cart;

    @BeforeEach
    private void init() {
        User user = User.builder()
                .name("supriya")
                .email("suppawar6@gmail.com")
                .about("This is testing create method")
                .gender("Female")
                .imgname("abc.png")
                .password("abcd")
                .build();

        cart = Cart.builder()
                .cartId("123")
                .createdAt(null)
                .user(user)
                .build();

    }


    public Object addItemToCart(Object any, String s) {
        return null;
    }
}
