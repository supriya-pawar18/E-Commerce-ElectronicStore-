package com.electronicstore.electronicStoreApp.ServiceI;

import com.electronicstore.electronicStoreApp.dto.CategoryDto;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;

public interface CategoryService {

    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
     CategoryDto update(CategoryDto categoryDto,String categoryId);

    //delete
    void delete(String categoryId);

    //get all
    PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sort,String sortDir);

    //get single category
    CategoryDto get(String categoryId);

}
