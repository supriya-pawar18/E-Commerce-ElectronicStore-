package com.electronicstore.electronicStoreApp.ServiceImpl;

import com.electronicstore.electronicStoreApp.ServiceI.CartService;
import com.electronicstore.electronicStoreApp.ServiceI.CategoryService;
import com.electronicstore.electronicStoreApp.dto.AddItemToCartRequest;
import com.electronicstore.electronicStoreApp.dto.CartDto;
import com.electronicstore.electronicStoreApp.entites.Cart;
import com.electronicstore.electronicStoreApp.entites.CartItem;
import com.electronicstore.electronicStoreApp.entites.Product;
import com.electronicstore.electronicStoreApp.entites.User;
import com.electronicstore.electronicStoreApp.exception.ResourceNotFoundException;
import com.electronicstore.electronicStoreApp.repository.CartRepository;
import com.electronicstore.electronicStoreApp.repository.ProductRepo;
import com.electronicstore.electronicStoreApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartDto addItemToCart(String id, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId= String.valueOf(request.getProductId());

        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart=null;
//        try{
//             cart= cartRepository.findByUser(user).get();
//        }catch( NoSuchFieldException e){
//             cart =new Cart();
//             cart.setCreatedAt(new Date());
//        }

        //perform cart operation
        List<CartItem> items=cart.getItems();
        items.stream().map(item ->{

            if(item.getProduct().getProductId().equals(productId)){

            }
            return item;
        }).collect(Collectors.toList());

        //create item
        CartItem cartItem=CartItem.builder()
                .quantity(quantity)
                .totalPrice(quantity * product.getPrice())
                .cart(cart)
                .product(product)
                .build();

        cart.getItems().add(cartItem);

        return null;
    }

    @Override
    public void removeItemFromCart(String id, int cartItem) {

    }

    @Override
    public void clearCart(String id) {

    }
}
