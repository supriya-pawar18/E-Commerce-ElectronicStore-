package com.electronicstore.electronicStoreApp.ServiceImpl;

import com.electronicstore.electronicStoreApp.ServiceI.CartService;
import com.electronicstore.electronicStoreApp.ServiceI.CategoryService;
import com.electronicstore.electronicStoreApp.dto.AddItemToCartRequest;
import com.electronicstore.electronicStoreApp.dto.CartDto;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public CartDto addItemToCart(String id, AddItemToCartRequest request) {
        return null;
    }

    @Override
    public void removeItemFromCart(String id, int cartItem) {

    }

    @Override
    public void clearCart(String id) {

    }
}
