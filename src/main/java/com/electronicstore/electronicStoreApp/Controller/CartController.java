package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.ServiceI.CartService;
import com.electronicstore.electronicStoreApp.dto.AddItemToCartRequest;
import com.electronicstore.electronicStoreApp.dto.ApiResponse;
import com.electronicstore.electronicStoreApp.dto.CartDto;
import lombok.AllArgsConstructor;
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

    @PostMapping("/{id}")
    public ResponseEntity<CartDto> addItemToCart(@PathVariable String id,@RequestBody AddItemToCartRequest request) {
        CartDto cartDto = cartService.addItemToCart(id, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @GetMapping("/{id}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart( @PathVariable String id,@PathVariable int itemId){
        cartService.removeItemFromCart(id,itemId);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Item is removed")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String id){

        cartService.clearCart(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Cart is not blank !!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCartByUser(@PathVariable String id) {
        CartDto cartDto = cartService.getCartByUser(id);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
    }
