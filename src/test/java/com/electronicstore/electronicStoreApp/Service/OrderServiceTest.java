package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.ServiceI.CartService;
import com.electronicstore.electronicStoreApp.ServiceI.OrderService;
import com.electronicstore.electronicStoreApp.dto.CartDto;
import com.electronicstore.electronicStoreApp.dto.CreateOrderRequest;
import com.electronicstore.electronicStoreApp.dto.OrderDto;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
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
import org.springframework.data.domain.*;

import java.util.*;

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
    List<OrderItem> orderItems = new ArrayList<>();


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

        orderItems.add(new OrderItem(2, 5, 25, product, order));

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

    @Test
    public void getOrderOfUserTest() {
        Order order2 = Order.builder().orderId("abd").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("Supriya").billingAddress("Nashik").billingPhone("987456321").orderDate(new Date())
                .user(user).orderItems(orderItems).build();

        Order order3 = Order.builder().orderId("acd").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("ashu").billingAddress("Pune").billingPhone("9763239803").orderDate(new Date())
                .user(user).orderItems(orderItems).build();


        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        orderList.add(order2);
        orderList.add(order3);
        String id = "abcd";
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        Mockito.when(orderRepo.findByUser(Mockito.any())).thenReturn(orderList);

        List<OrderDto> orderOfUser = orderService.getOrdersOfUser(id);

        Assertions.assertEquals(orderList.size(), orderOfUser.size());

    }

    @Test
    void getOrdersTest() {
        Order order2 = Order.builder().orderId("abcd").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("Supriya").billingAddress("Pune").billingPhone("893489234").orderDate(new Date())
                .user(user).orderItems(orderItems).build();

        Order order3 = Order.builder().orderId("abcd").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("AShu").billingAddress("Nashik").billingPhone("9763239803").orderDate(new Date())
                .user(user).orderItems(orderItems).build();
        List<Order> list = Arrays.asList(order, order2, order3);
        Page<Order> orderlist = new PageImpl<>(list);
        Mockito.when(orderRepo.findAll((Pageable) Mockito.any())).thenReturn(orderlist);
        Sort sort = Sort.by("name").ascending();

        PageRequest request = PageRequest.of(1, 2, sort);
        PageableResponse<OrderDto> allProduct = orderService.getOrders("1", 1, 10, "asc","a");
      //  Assertions.assertEquals(3, allProduct.getContent().size());


    }

    }
