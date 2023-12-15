package com.electronicstore.electronicStoreApp.ServiceImpl;

import com.electronicstore.electronicStoreApp.ServiceI.CategoryService;
import com.electronicstore.electronicStoreApp.dto.CategoryDto;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.entites.Category;
import com.electronicstore.electronicStoreApp.exception.ResourceNotFoundException;
import com.electronicstore.electronicStoreApp.helper.AppContants;
import com.electronicstore.electronicStoreApp.helper.Helper;
import com.electronicstore.electronicStoreApp.repository.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    private Logger logger= LoggerFactory.getLogger(CategoryServiceImpl.class);


    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        //categoryId random
        logger.info("Initiating dao request for creating category record");
        String id = UUID.randomUUID().toString();
        categoryDto.setCategoryId(id);
        Category category = modelMapper.map(categoryDto, Category.class);
        Category save = categoryRepo.save(category);
        logger.info("Completed dao request for creating category record");

        return modelMapper.map(save,CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {

        logger.info("Initiating dao request for updating category record with category id {}:",categoryId);

        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppContants.CATEGORY_NOT_FOUND));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatecategory = categoryRepo.save(category);

        logger.info("Completed dao request for updating category record with category id {} :",categoryId);
        return modelMapper.map(updatecategory,CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        logger.info("Initiating dao request for deleting category record with category id {}:",categoryId);
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppContants.CATEGORY_DELETED));
        logger.info("Completed dao request for deleting category record with category id {}:",categoryId);
        categoryRepo.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        logger.info("Initiating dao request for getting all category record");
        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize);
        Page<Category> page = categoryRepo.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        logger.info("Completed dao request for getting all category record");
        return pageableResponse;
    }

    @Override
    public CategoryDto get(String categoryId) {
        logger.info("Initiating dao request for getting single category record using categoryId{} :",categoryId);
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppContants.CATEGORY_NOT_FOUND));
        logger.info("Completed dao request for getting single category record using categoryId {}:",categoryId);
        return modelMapper.map(category,CategoryDto.class);
    }
}
