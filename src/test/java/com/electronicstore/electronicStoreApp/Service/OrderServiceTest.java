package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.ServiceI.CartService;
import com.electronicstore.electronicStoreApp.ServiceI.OrderService;
import com.electronicstore.electronicStoreApp.dto.CreateOrderRequest;
import com.electronicstore.electronicStoreApp.dto.OrderDto;
import com.electronicstore.electronicStoreApp.entites.*;
import com.electronicstore.electronicStoreApp.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceTest {

    @Autowired
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
    private CartRepository cartRepository;
    @MockBean
    private OrderItemRepo orderItemRepo;
    Order order;
    Cart cart;
    User user;
    Product product;
    List<OrderItem> items = new ArrayList<>();

    @BeforeEach
    private void init() {
         user = User.builder()
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

        order = Order.builder().orderId("abcd").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("Supriya").billingAddress("Pune").billingPhone("987456321").orderDate(new Date())
                .user(user).build();

      //  items.add(new CartItem(1, product, 2, 30, cart));

    }

    @Test
    public void createOrderTest() {

        CreateOrderRequest orderRequest = CreateOrderRequest.builder()
                .cartId("abcd")
                .id("fgf")
                .orderStatus("PENDING")
                .paymentStatus("NOTPAID")
                .billingAddress("Pune")
                .billingPhone("9874569852")
                .billingName("Supriya").build();

        String id="jhj";

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(cartRepository.findById(Mockito.anyString())).thenReturn(Optional.of(cart));
        Mockito.when(cartRepository.save(Mockito.any())).thenReturn(cart);

        Mockito.when(orderRepo.save(Mockito.any())).thenReturn(order);
       // Order orderSaved = orderRepo.save(order);

       // OrderDto order1 = this.orderService.createOrder(orderRequest);
//        System.out.println(order1);
//        Assertions.assertEquals(order.getBillingName(), order1.getBillingName(), "Billing name doesnot matches");

    }


    @Test
    public void removeOrderTest() {
        String orderId = "abcdefghijklm";

        Mockito.when(orderRepo.findById(orderId)).thenReturn(Optional.of(order));
        orderService.removeOrder(orderId);
        Mockito.verify(orderRepo, Mockito.times(1)).delete(order);
    }

    }
