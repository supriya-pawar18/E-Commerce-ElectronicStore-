package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.ServiceI.CartService;
import com.electronicstore.electronicStoreApp.dto.AddItemToCartRequest;
import com.electronicstore.electronicStoreApp.dto.CartDto;
import com.electronicstore.electronicStoreApp.dto.CategoryDto;
import com.electronicstore.electronicStoreApp.entites.Cart;
import com.electronicstore.electronicStoreApp.entites.Product;
import com.electronicstore.electronicStoreApp.entites.User;
import com.electronicstore.electronicStoreApp.repository.CartRepository;
import com.electronicstore.electronicStoreApp.repository.ProductRepo;
import com.electronicstore.electronicStoreApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class CartServiceTest {

    @MockBean
    private CartService cartService;
    @MockBean
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;
    @MockBean
    private ProductRepo productRepo;
    @MockBean
    private UserRepository userRepository;
    Cart cart;

    User user;
    Product product;

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

        product = Product.builder()
                .price(123)
                .title("Krishna")
                .description("Java Dev")
                .discountedPrice(122)
                .productImage("k.jpg")
                .quantity(12)
                .addedDate(null)
                .category(null)
                .stock(true)
                .build();
    }

//    @Test
//    public void addItemToCartTest() {
//        AddItemToCartRequest cartRequest = AddItemToCartRequest.builder()
//                .productId("12344")
//                .quantity(123)
//                .build();
//
//        String id = "1234";
//
//        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
//
//        Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
//
//        CartDto cartDto = cartService.addItemToCart(id, cartRequest);
//        Cart updateCart = cartRepository.save(cart);
//      //  Assertions.assertNull(updateCart);
//        System.out.println(cart);
//      //  Assertions.assertNotNull(cart);
//
//        //Assertions.assertEquals("mobile", categoryDto.getTitle());
//    }
}
