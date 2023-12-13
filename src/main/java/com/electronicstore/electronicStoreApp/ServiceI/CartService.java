package com.electronicstore.electronicStoreApp.ServiceI;

import com.electronicstore.electronicStoreApp.dto.AddItemToCartRequest;
import com.electronicstore.electronicStoreApp.dto.CartDto;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    //cart is available add item
    CartDto addItemToCart(String id, AddItemToCartRequest request);

    //remove item from cart
    void removeItemFromCart(String id,int cartItem);

    //remove all items
    void clearCart(String id);
}
