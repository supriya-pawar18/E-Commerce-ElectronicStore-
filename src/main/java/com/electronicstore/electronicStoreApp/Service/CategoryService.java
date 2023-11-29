package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.dto.CategoryDto;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import org.springframework.data.domain.Page;

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
