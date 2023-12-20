package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.ServiceI.OrderService;
import com.electronicstore.electronicStoreApp.dto.ApiResponse;
import com.electronicstore.electronicStoreApp.dto.CreateOrderRequest;
import com.electronicstore.electronicStoreApp.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest request) {

        OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId) {

        orderService.removeOrder(orderId);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Order is removed !!")
                .success(true)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    //get orders of user
    @GetMapping("/user/{id}")
    public  ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String id){

        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(id);
        return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
    }

}

