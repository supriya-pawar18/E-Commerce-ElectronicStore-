package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.dto.ProductDto;

import java.util.List;

public interface ProductService {

    //create
    ProductDto create(ProductDto productDto);

    //update
    ProductDto update(ProductDto productDto);

    //delete
    void delet(String productId);

    //get single
    ProductDto get(String productId);

    //get all
    List<ProductDto> getAll();

    //get all:live
    List<ProductDto> getAllLive();

    //search product
    List<ProductDto> searchByTitle(String subTitle);

}
