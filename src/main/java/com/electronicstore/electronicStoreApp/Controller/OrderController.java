package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.ServiceI.OrderService;
import com.electronicstore.electronicStoreApp.ServiceImpl.OrderServiceImpl;
import com.electronicstore.electronicStoreApp.dto.ApiResponse;
import com.electronicstore.electronicStoreApp.dto.CreateOrderRequest;
import com.electronicstore.electronicStoreApp.dto.OrderDto;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger= LoggerFactory.getLogger(OrderController.class);


    @PostMapping("/")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest request) {
        logger.info("Initiating dao request for creating order");
        OrderDto order = orderService.createOrder(request);
        logger.info("Completed dao call to create order");
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId) {
        logger.info("Initiating dao request for removing order with orderId {}:",orderId);
        orderService.removeOrder(orderId);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("Order is removed !!")
                .success(true)
                .build();
        logger.info("Completed dao call to removing order with orderId {}:",orderId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    //get orders of user
    @GetMapping("/user/{id}")
    public  ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String id){
        logger.info("Initiating dao request for getting order of user with user id {}:",id);
        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(id);
        logger.info("Completed dao call to getting order of user with user id {}:",id);
        return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PageableResponse<OrderDto>> getOrders(@PathVariable String id,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        logger.info("Initiating dao request for getting order with user id{}:",id);
        PageableResponse<OrderDto> orders = orderService.getOrders(id,pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completed dao call to get order of user with userId:{}", id);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }

}

