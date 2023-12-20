package com.electronicstore.electronicStoreApp.ServiceImpl;

import com.electronicstore.electronicStoreApp.ServiceI.OrderService;
import com.electronicstore.electronicStoreApp.dto.CreateOrderRequest;
import com.electronicstore.electronicStoreApp.dto.OrderDto;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.entites.*;
import com.electronicstore.electronicStoreApp.exception.BadApiRequestException;
import com.electronicstore.electronicStoreApp.exception.ResourceNotFoundException;
import com.electronicstore.electronicStoreApp.repository.CartRepository;
import com.electronicstore.electronicStoreApp.repository.OrderRepo;
import com.electronicstore.electronicStoreApp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
       String id=orderDto.getId();
       String cartId=orderDto.getCartId();

        //fetch user
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        //fetch cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart with given id not found"));
        List<CartItem> cartItems=cart.getItems();

        if(cartItems.size() <= 0){
            throw new BadApiRequestException("Invalid number of items !!");
        }

        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();


        AtomicReference<Integer> orderAmount=new AtomicReference<>(0);
        //
        List<OrderItem> orderItems= cartItems.stream().map(cartItem -> {
            //cartitem=> orderItem
             OrderItem orderItem=OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getTotalPrice())
                    .order(order)
                    .build();

             orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        cart.getItems().clear();
        cartRepository.save(cart);
        Order orderSaved = orderRepo.save(order);

        return modelMapper.map(orderSaved,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

        Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found !!"));
        orderRepo.delete(order);
    }

    @Override
    public List<OrderDto> getOrdersOfUser(String id) {

        return null;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return null;
    }
}
