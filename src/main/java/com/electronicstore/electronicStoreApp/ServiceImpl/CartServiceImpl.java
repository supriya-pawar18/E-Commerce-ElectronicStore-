package com.electronicstore.electronicStoreApp.ServiceImpl;

import com.electronicstore.electronicStoreApp.ServiceI.CartService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
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

    private Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public CartDto addItemToCart(String id, AddItemToCartRequest request) {

        logger.info("Initiating dao request for adding items into cart  with user id {}:",id);
        int quantity = request.getQuantity();
        String productId = String.valueOf(request.getProductId());

        if (quantity <= 0) {
            throw new BadApiRequestException(AppContants.INVALID_QUANTITY);
        }
        //fetch product
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
         //fetch user
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
                //item alreadypresent
                 item.setQuantity(quantity + item.getQuantity()); // change
                item.setTotalPrice(quantity * product.getPrice() + item.getTotalPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

              cart.setItems(updatedITems);

        //create item
        if(!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getItems().add(cartItem);
        }
        cart.setUser(user);
        Cart updateCart = cartRepository.save(cart);
        logger.info("Completed dao call to add item into cart with userId:{}", id);
        return modelMapper.map(updateCart, CartDto.class);
    }



    @Override
    public void removeItemFromCart(String id, int cartItem) {
        logger.info("Initiating dao request for removing items from cart with user id {}:",id);
        CartItem cart1=cartIemRepo.findById(cartItem).orElseThrow(()->new ResourceNotFoundException(AppContants.ITEM_NOT_FOUND));
        logger.info("Completed dao call to remove item from cart with userId:{}", id);
        cartIemRepo.delete(cart1);
    }

    @Override
    public void clearCart(String id) {
        logger.info("Initiating dao request for clear items from cart with user id {}:",id);
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(AppContants.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException(AppContants.ITEM_NOT_FOUND));

        cart.getItems().clear();
        cartRepository.save(cart);
        logger.info("Completed dao call to clear item from cart with userId:{}", id);

    }

    @Override
    public CartDto getCartByUser(String id) {
        logger.info("Initiating dao request for get cart by user items from cart with user id {}:",id);
        User user = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(AppContants.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException(AppContants.ITEM_NOT_FOUND));

        logger.info("Completed dao call to get cartby user item from cart with userId:{}", id);
        return modelMapper.map(cart,CartDto.class);
    }
}
