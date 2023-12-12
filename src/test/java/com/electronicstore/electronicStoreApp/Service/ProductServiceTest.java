package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.ServiceI.ProductService;
import com.electronicstore.electronicStoreApp.ServiceI.UserServiceI;
import com.electronicstore.electronicStoreApp.dto.ProductDto;
import com.electronicstore.electronicStoreApp.dto.UserDto;
import com.electronicstore.electronicStoreApp.entites.Product;
import com.electronicstore.electronicStoreApp.entites.User;
import com.electronicstore.electronicStoreApp.repository.ProductRepo;
import com.electronicstore.electronicStoreApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    private ProductRepo productRepo;

    @Autowired
    private ProductService productService;

    Product product;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    private void init() {

        product = Product.builder()
                .title("Samsumg")
                .description("The details related to info")
                .price(50000)
                .discountedPrice(40000)
                .quantity(40)
                .live(false)
                .stock(false)
                .build();
    }

    @Test
    public void createProductTest() {
        Mockito.when(productRepo.save(Mockito.any())).thenReturn(product);
        ProductDto product1 = productService.create(modelMapper.map(product, ProductDto.class));
        System.out.println(product1.getTitle());
        Assertions.assertNotNull(product1);

        Assertions.assertEquals("Samsumg", product1.getTitle());
    }
}
