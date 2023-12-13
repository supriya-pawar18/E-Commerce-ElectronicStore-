package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.ServiceI.CategoryService;
import com.electronicstore.electronicStoreApp.ServiceI.ProductService;
import com.electronicstore.electronicStoreApp.dto.CategoryDto;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.dto.ProductDto;
import com.electronicstore.electronicStoreApp.dto.UserDto;
import com.electronicstore.electronicStoreApp.entites.Category;
import com.electronicstore.electronicStoreApp.entites.Product;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    private Product product;

    @Autowired
    private MockMvc mockMvc;

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
    public void createProductTest() throws Exception{

        ProductDto dto=modelMapper.map(product, ProductDto.class);

        Mockito.when(productService.create(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/category/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    private String convertObjectToJsonString(Product product) {
        try{
            return new ObjectMapper().writeValueAsString(product);
        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Test
    public void updateProductTest() throws Exception {
        String productId="12";
        ProductDto dto=this.modelMapper.map(product,ProductDto.class);
        Mockito.when(productService.update(Mockito.any(),Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/product/updateProd/" + productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(product))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());

    }

    @Test
    public void getAllCatTest() throws Exception {
        Product product = Product.builder()
                .title("IPhone")
                .description("The details related to Iphone")
                .price(90000)
                .discountedPrice(80000)
                .quantity(30)
                .live(false)
                .stock(false)
                .build();

         Product product1 = Product.builder()
                .title("Samsumg")
                .description("The details related to Samsumg")
                .price(50000)
                .discountedPrice(40000)
                .quantity(40)
                .live(false)
                .stock(false)
                .build();

        List<Product> prod = Arrays.asList(product, product1);
        List<ProductDto> categoryDtos = prod.stream().map((abc) -> this.modelMapper.map(prod, ProductDto.class)).collect(Collectors.toList());
        PageableResponse pagableResponce = new PageableResponse();
        Mockito.when(productService.getAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pagableResponce);

        mockMvc.perform(MockMvcRequestBuilders.get("/product/getAll")
                        .param("pageNumber", "1")
                        .param("pageSize", "10")
                        .param("sortDir", "asc")
                        .param("sortBy", "name")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getProductTest() throws Exception {
        String productId = "abcd";

        ProductDto dto = modelMapper.map(product, ProductDto.class);
        Mockito.when(productService.get(Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void deleteProduct() throws Exception {

        String productId="abc";

        Mockito.doNothing().when(productService).delete(productId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/product/" + productId))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
