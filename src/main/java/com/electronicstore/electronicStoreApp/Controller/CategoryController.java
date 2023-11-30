package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.Service.CategoryService;
import com.electronicstore.electronicStoreApp.Service.FileService;
import com.electronicstore.electronicStoreApp.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    //create
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/updateCat/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId,@RequestBody CategoryDto categoryDto){
        CategoryDto update = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId){
         categoryService.delete(categoryId);
        ApiResponse response = ApiResponse.builder().message("Category is successfully !!").status(HttpStatus.OK).build();
         return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //get all
    @GetMapping("/getAll")
    public  ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
        PageableResponse<CategoryDto> all = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    //get single
    @GetMapping("/getSingle/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){
        CategoryDto categoryDto = categoryService.get(categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    @PostMapping("/image/{id}")
    public  ResponseEntity<ImageResponse> uploadingImage(@RequestParam("categoryImage") MultipartFile coverImage, @PathVariable String categoryId) throws IOException {

        String imageName = fileService.upLoadFile(coverImage, imageUploadPath);

        CategoryDto categoryDto = categoryService.get(categoryId);
        categoryDto.setCoverImage(String.valueOf(coverImage));
        CategoryDto update = categoryService.update(categoryDto, categoryId);

        ImageResponse imageResponse=ImageResponse.builder().imgname(imageName).success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    }

    //server user img
    @GetMapping(value = "/getImage/{id}")
    public void serveUserImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        CategoryDto categoryDto = this.categoryService.get(categoryId);
        InputStream inputStream = fileService.getResource(imageUploadPath, categoryDto.getCoverImage());
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(inputStream,response.getOutputStream());
    }


}
