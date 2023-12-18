package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.ServiceI.CartService;
import com.electronicstore.electronicStoreApp.ServiceImpl.CategoryServiceImpl;
import com.electronicstore.electronicStoreApp.dto.AddItemToCartRequest;
import com.electronicstore.electronicStoreApp.dto.ApiResponse;
import com.electronicstore.electronicStoreApp.dto.CartDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    private Logger logger= LoggerFactory.getLogger(CartController.class);


    @PostMapping("/{id}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String id,@RequestBody AddItemToCartRequest request) {
        logger.info("Initiating dao request for add item to cart with user id {}:",id);
        CartDto cartDto = cartService.addItemToCart(id, request);
        logger.info("Completed dao call to add item into cart with userId:{}", id);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart( @PathVariable String id,@PathVariable int itemId){
        logger.info("Initiating dao request for remove item from cart with user id {}:",id);
        cartService.removeItemFromCart(id,itemId);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Item is removed")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        logger.info("Completed dao call to remove item from cart with userId:{}", id);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String id){
        logger.info("Initiating dao request for clear item from cart with user id {}:",id);
        cartService.clearCart(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Cart is not blank !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();

        logger.info("Completed dao call to clear item from cart with userId:{}", id);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable String id) {
        logger.info("Initiating dao request for get cart item by user from cart with user id {}:",id);
        CartDto cartDto = cartService.getCartByUser(id);

        logger.info("Completed dao call to get cart item by user from cart with userId:{}", id);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
    }
