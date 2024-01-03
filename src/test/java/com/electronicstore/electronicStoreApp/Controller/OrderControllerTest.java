package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.ServiceI.OrderService;
import com.electronicstore.electronicStoreApp.dto.*;
import com.electronicstore.electronicStoreApp.entites.Cart;
import com.electronicstore.electronicStoreApp.entites.Order;
import com.electronicstore.electronicStoreApp.entites.User;
import com.electronicstore.electronicStoreApp.repository.OrderRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private OrderRepo orderRepo;
    @MockBean
    private OrderService orderService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;
    User user;

    Cart cart;
    Order order;

    @BeforeEach
    private void init() {

        user= User.builder()
                .name("supriya")
                .email("suppawar6@gmail.com")
                .about("This is testing controller ")
                .gender("Female")
                .imgname("abc.png")
                .password("abcd")
                .build();

        cart= Cart.builder()
                .cartId("23")
                .createdAt(null)
                .user(user)
                .build();

        Order order = Order.builder()
                .orderId("12344")
                .orderAmount(7657)
                .billingAddress("Pune")
                .billingPhone("4859379")
                .billingName("ash")
                .build();
    }
    @Test
    public void createOrderTest() throws Exception {

        CreateOrderRequest orderRequest = CreateOrderRequest.builder()
                .cartId("123")
                .id("1234")
                .orderStatus("Pending")
                .paymentStatus("Not_Paid")
                .billingAddress("Nashik")
                .billingPhone("000")
                .build();

        OrderDto orderDto = this.modelMapper.map(order, OrderDto.class);

        Mockito.when(orderService.createOrder(Mockito.any())).thenReturn(orderDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(orderRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    private String convertObjectToJsonString(Object orderRequest) {
        try{
            return new ObjectMapper().writeValueAsString(orderRequest);
        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void removeOrderTest() throws Exception {

        String orderId="abc";

        Mockito.doNothing().when(orderService).removeOrder(orderId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/order/remove/", orderId))
                .andDo(print());
              //  .andExpect(status().isOk());
    }

    @Test
    public void getOrderOfUserTest() throws Exception {
        String id = "abcd";

        Order order2 = Order.builder().orderId("poer").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("Supriya").billingAddress("Pune").billingPhone("987456321").orderDate(new Date())
                .user(user).build();

        Order order3 = Order.builder().orderId("stur").orderStatus("PENDING").paymentStatus("NOTPAID").orderAmount(200)
                .billingName("vvk").billingAddress("Pune").billingPhone("987456321").orderDate(new Date())
                .user(user).build();

        List<Order> list = new ArrayList<>();
        list.add(order2);
        list.add(order3);

        List<OrderDto> dtos = list.stream().map(i -> modelMapper.map(i, OrderDto.class)).collect(Collectors.toList());
        Mockito.when(orderService.getOrdersOfUser(Mockito.anyString())).thenReturn(dtos);

        mockMvc.perform(MockMvcRequestBuilders.get("/order/user/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)

                )
                .andDo(print())
                .andExpect(status().isOk());
    }


}
