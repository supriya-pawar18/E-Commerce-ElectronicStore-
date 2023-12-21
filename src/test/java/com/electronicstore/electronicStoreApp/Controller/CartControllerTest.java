package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.Service.CartService;
import com.electronicstore.electronicStoreApp.dto.AddItemToCartRequest;
import com.electronicstore.electronicStoreApp.dto.CartDto;
import com.electronicstore.electronicStoreApp.dto.CategoryDto;
import com.electronicstore.electronicStoreApp.entites.Cart;
import com.electronicstore.electronicStoreApp.entites.Category;
import com.electronicstore.electronicStoreApp.entites.User;
import com.electronicstore.electronicStoreApp.repository.CartRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest{
    @MockBean
    private CartService cartService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;
    Cart cart;
    User user;

    @BeforeEach
    private void init() {

        user= User.builder()
                .name("supriya")
                .email("suppawar6@gmail.com")
                .about("This is testing controller ")
                .gender("Female")
                .imgname("abc.png")
                .password("abcd")
                .build();

        cart=Cart.builder()
                .cartId("23")
                .createdAt(null)
                .user(user)
                .build();
    }

    @Test
    public void addItemToCartTest() throws Exception{
        AddItemToCartRequest cartRequest = AddItemToCartRequest.builder()
                .productId("12344")
                .quantity(123)
                .build();

        String id = "1234";

        CartDto dto=modelMapper.map(cart, CartDto.class);
        Mockito.when(cartService.addItemToCart(Mockito.anyString(),Mockito.any())).thenReturn(dto);

                mockMvc.perform(MockMvcRequestBuilders.post("/cart/addItemToCart/{id}" , id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(cartRequest))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
               // .andExpect(jsonPath("$.title").exists());

    }
    private String convertObjectToJsonString(Object cartRequest) {
        try{
            return new ObjectMapper().writeValueAsString(cartRequest);
        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
