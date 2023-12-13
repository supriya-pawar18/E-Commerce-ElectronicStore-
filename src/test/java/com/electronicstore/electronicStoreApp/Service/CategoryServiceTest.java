package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.ServiceI.CategoryService;
import com.electronicstore.electronicStoreApp.dto.CategoryDto;
import com.electronicstore.electronicStoreApp.dto.UserDto;
import com.electronicstore.electronicStoreApp.entites.Category;
import com.electronicstore.electronicStoreApp.entites.User;
import com.electronicstore.electronicStoreApp.repository.CategoryRepo;
import com.electronicstore.electronicStoreApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    @MockBean
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryService categoryService;

    Category category;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    private void init() {

        category = Category.builder()
                .title("mobile")
                .description("This is mobile related data")
                .coverImage("abc.png")
                .build();
    }

    @Test
    public void createCatTest() {
        Mockito.when(categoryRepo.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto = categoryService.create(modelMapper.map(category, CategoryDto.class));
        System.out.println(categoryDto.getTitle());
        Assertions.assertNotNull(categoryDto);

        Assertions.assertEquals("mobile", categoryDto.getTitle());
    }

    @Test
    public void upadateCat() {

        String categoryId = "bacdefcgf";

        CategoryDto categoryDto = CategoryDto.builder()
                .title("mobile")
                .description("This is mobile related data")
                .coverImage("abc.png")
                .build();

        Mockito.when(categoryRepo.findById(Mockito.anyString())).thenReturn(Optional.of(category));
        Mockito.when(categoryRepo.save(Mockito.any())).thenReturn(category);

        CategoryDto updateCat = categoryService.update(categoryDto, categoryId);
        System.out.println(updateCat.getTitle());
        Assertions.assertNotNull(categoryDto);

        Assertions.assertEquals(categoryDto.getTitle(), updateCat.getTitle(), "Name is not equal");

    }

    @Test
    public void deleteCatTest() {

        String categoryId = "abcdefghijklm";

        Mockito.when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));

        categoryService.delete(categoryId);

        Mockito.verify(categoryRepo, Mockito.times(1)).delete(category);

    }
    @Test
    public void getAllCatTest(){

        Category category = Category.builder()
                .title("tv")
                .description("This is tv related information")
                .coverImage("gef.png")
                .build();

        Category category1 = Category.builder()
                .title("mobile")
                .description("This is mobile related data")
                .coverImage("lmn.png")
                .build();

        List<Category> CatList = Arrays.asList(category, category1);

        Page<Category> page = new PageImpl<>(CatList);

        Mockito.when(categoryRepo.findAll((Pageable) Mockito.any())).thenReturn(page);

        Sort sort = Sort.by("name").ascending();

    }

    @Test
    public void getCategoryTest(){
        String categoryId="abcdesx";
        Mockito.when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));

        CategoryDto categoryDto= categoryService.get(categoryId);
        Assertions.assertNotNull(categoryDto);
        Assertions.assertEquals(category.getTitle(),categoryDto.getTitle(),"Title not matched");
    }
}
