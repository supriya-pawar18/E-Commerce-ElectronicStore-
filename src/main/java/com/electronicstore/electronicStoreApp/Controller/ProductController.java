package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.ServiceI.ProductService;
import com.electronicstore.electronicStoreApp.dto.ApiResponse;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.dto.ProductDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private Logger logger= LoggerFactory.getLogger(ProductController.class);

    //create
    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        logger.info("Entering request for creating new product record");
        ProductDto productDto1 = productService.create(productDto);
        logger.info("Completed request for creating new product record");
        return new ResponseEntity<>(productDto1, HttpStatus.CREATED);
    }

    //update
    @GetMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable String productId){
        logger.info("Entering request for updating product record with product id {}:",productId);
        ProductDto updatedPro = productService.update(productDto,productId);
        logger.info("Completed request for updating product record with product id {}:",productId);
        return new ResponseEntity<>(updatedPro, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProd(@PathVariable String productId){
        logger.info("Entering request for deleting product record with product id {}:",productId);
        productService.delete(productId);

        ApiResponse apiResponse = ApiResponse.builder().message("Product deleted successfully !!").status(HttpStatus.OK).success(true).build();
        logger.info("Completed request for deleting product record with product id{}:",productId);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    //get single
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String productId){
        logger.info("Entering request for getting single product record with product id {} :",productId);
        ProductDto getproduct = productService.get(productId);
        logger.info("completed request for getting single product record with product id {}:",productId);
        return new ResponseEntity<>(getproduct, HttpStatus.OK);
    }

    //get all
    @GetMapping("/")
    public ResponseEntity<PageableResponse<ProductDto>> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<ProductDto> response = productService.getAll(pageNumber,pageSize,sortBy,sortDir);
        logger.info("Completes request for getting product record");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //get all live
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<ProductDto> response = productService.getAllLive(pageNumber,pageSize,sortBy,sortDir);
        logger.info("Completes request for getting all live product record");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //get search
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchByTitle(
            @PathVariable String query,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<ProductDto> response = productService.searchByTitle(query,pageNumber,pageSize,sortBy,sortDir);
        logger.info("Completes request for searching by title product record");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }


}
