package com.electronicstore.electronicStoreApp.ServiceImpl;

import com.electronicstore.electronicStoreApp.ServiceI.CartService;
import com.electronicstore.electronicStoreApp.ServiceI.CategoryService;
import com.electronicstore.electronicStoreApp.dto.AddItemToCartRequest;
import com.electronicstore.electronicStoreApp.dto.CartDto;
import com.electronicstore.electronicStoreApp.entites.Cart;
import com.electronicstore.electronicStoreApp.entites.CartItem;
import com.electronicstore.electronicStoreApp.entites.Product;
import com.electronicstore.electronicStoreApp.entites.User;
import com.electronicstore.electronicStoreApp.exception.BadApiRequestException;
import com.electronicstore.electronicStoreApp.exception.ResourceNotFoundException;
import com.electronicstore.electronicStoreApp.helper.AppContants;
import com.electronicstore.electronicStoreApp.repository.CartIemRepo;
import com.electronicstore.electronicStoreApp.repository.CartRepository;
import com.electronicstore.electronicStoreApp.repository.ProductRepo;
import com.electronicstore.electronicStoreApp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartIemRepo cartIemRepo;

    @Override
    public CartDto addItemToCart(String id, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = String.valueOf(request.getProductId());

        if (quantity <= 0) {
            throw new BadApiRequestException(AppContants.INVALID_QUANTITY);
        }

        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart=null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }


        AtomicReference<Boolean> updated = new AtomicReference<>(false);

        // perform cart operation
        List<CartItem> items=cart.getItems();
         List<CartItem> updatedITems =items.stream().map(item ->{

            if(item.getProduct().getProductId().equals(productId)){
                 item.setQuantity(quantity + item.getQuantity()); // change
                item.setTotalPrice(quantity * product.getPrice() + item.getTotalPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

              cart.setItems(updatedITems);

        //create item
         if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                         .quantity(quantity)
                         .totalPrice(quantity * product.getPrice())
                         .cart(cart)
                         .product(product)
                         .build();

            cart.getItems().add(cartItem);
        }
        cart.setUser(user);
        Cart updateCart = cartRepository.save(cart);
      //  logger.info("Completed dao call to add item into cart with userId:{}", userId);
        return modelMapper.map(updateCart, CartDto.class);
    }



    @Override
    public void removeItemFromCart(String id, int cartItem) {

//          CartItem cart1=cartIemRepo.findById(cartItem).orElseThrow(()->new ResourceNotFoundException("Item Not found in cart !!"));
//          cartIemRepo.delete(cart1);
    }

    @Override
    public void clearCart(String id) {

    }
}
