package com.electronicstore.electronicStoreApp.ServiceImpl;

import com.electronicstore.electronicStoreApp.ServiceI.OrderService;
import com.electronicstore.electronicStoreApp.dto.CreateOrderRequest;
import com.electronicstore.electronicStoreApp.dto.OrderDto;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.entites.*;
import com.electronicstore.electronicStoreApp.exception.BadApiRequestException;
import com.electronicstore.electronicStoreApp.exception.ResourceNotFoundException;
import com.electronicstore.electronicStoreApp.helper.Helper;
import com.electronicstore.electronicStoreApp.repository.CartRepository;
import com.electronicstore.electronicStoreApp.repository.OrderRepo;
import com.electronicstore.electronicStoreApp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private Logger logger= LoggerFactory.getLogger(OrderServiceImpl.class);


    @Override
    public OrderDto createOrder(CreateOrderRequest request) {
        logger.info("Initiating dao request for creating order");

        String id=request.getId();
       String cartId=request.getCartId();

        //fetch user
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with given id"));
        //fetch cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart with given id not found"));

        List<CartItem> cartItems=cart.getItems();

        if(cartItems.size() <= 0){
            throw new BadApiRequestException("Invalid number of items !!");
        }

        Order order = Order.builder()
                .billingName(request.getBillingName())
                .billingPhone(request.getBillingPhone())
                .billingAddress(request.getBillingAddress())
                .orderDate(new Date())
                .deliveredDate(null)
                .paymentStatus(request.getPaymentStatus())
                .orderStatus(request.getOrderStatus())
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
        logger.info("Completed dao call to create order");
        return modelMapper.map(orderSaved,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        logger.info("Initiating dao request for removing order with orderId{} :",orderId);
        Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found !!"));

        logger.info("Completed dao call to remove order with userId:{}", orderId);
        orderRepo.delete(order);
    }

    @Override
    public List<OrderDto> getOrdersOfUser(String id) {
        logger.info("Initiating dao request for getting order with user id{}:",id);
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Order> orders = orderRepo.findByUser(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        logger.info("Completed dao call to get order of user with userId:{}", id);
        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(String id,int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> page = orderRepo.findAll(pageable);

        logger.info("Completed dao call to get order with userId:{}",id);
        return Helper.getPageableResponse(page,OrderDto.class);

    }
}
