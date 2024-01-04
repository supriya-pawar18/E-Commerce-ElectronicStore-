package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.ServiceI.CartService;
import com.electronicstore.electronicStoreApp.ServiceI.OrderService;
import com.electronicstore.electronicStoreApp.entites.*;
import com.electronicstore.electronicStoreApp.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceTest {

    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderRepo orderRepo;
    @Autowired
    private ModelMapper modelMapper;
    @MockBean
    private ProductRepo productRepo;
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderItemRepo orderItemRepo;
    Order order;
    Cart cart;
    User user;
    Product product;
    List<OrderItem> items = new ArrayList<>();

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

      //  items.add(new CartItem(1, product, 2, 30, cart));

    }

    @Test
    public void removeOrderTest() {
        String orderId = "abcd";
        Mockito.when(orderRepo.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(order));
        orderService.removeOrder(orderId);
        Mockito.verify(orderRepo, Mockito.times(1)).delete(order);
    }

    }
