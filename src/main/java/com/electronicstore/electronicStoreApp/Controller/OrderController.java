package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.ServiceI.OrderService;
import com.electronicstore.electronicStoreApp.dto.ApiResponse;
import com.electronicstore.electronicStoreApp.dto.CreateOrderRequest;
import com.electronicstore.electronicStoreApp.dto.OrderDto;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
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

    @GetMapping("/{id}")
    public ResponseEntity<PageableResponse<OrderDto>> getOrders(@PathVariable String id,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

}

