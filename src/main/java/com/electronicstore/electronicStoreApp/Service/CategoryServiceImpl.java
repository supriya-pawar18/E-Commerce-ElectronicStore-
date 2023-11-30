package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.dto.CategoryDto;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.entites.Category;
import com.electronicstore.electronicStoreApp.exception.ResourceNotFoundException;
import com.electronicstore.electronicStoreApp.helper.Helper;
import com.electronicstore.electronicStoreApp.repository.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        //categoryId random

        String id = UUID.randomUUID().toString();
        categoryDto.setCategoryId(id);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category save = categoryRepo.save(category);
        return modelMapper.map(save,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found !!"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatecategory = categoryRepo.save(category);

        return modelMapper.map(updatecategory,CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found !!"));
        categoryRepo.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize);
        Page<Category> page = categoryRepo.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found !!"));
        return modelMapper.map(category,CategoryDto.class);
    }
}
