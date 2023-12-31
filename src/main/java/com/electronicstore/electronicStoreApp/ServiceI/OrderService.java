package com.electronicstore.electronicStoreApp.ServiceI;


import com.electronicstore.electronicStoreApp.dto.CreateOrderRequest;
import com.electronicstore.electronicStoreApp.dto.OrderDto;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderService{

     //create order
     OrderDto createOrder(CreateOrderRequest request);

     //remove order
    void removeOrder(String orderId);

     //get orders of user
    List<OrderDto> getOrdersOfUser(String id);

     //get orders
    PageableResponse<OrderDto> getOrders(String id,int pageNumber, int pageSize, String sortBy, String sortDir);


}
