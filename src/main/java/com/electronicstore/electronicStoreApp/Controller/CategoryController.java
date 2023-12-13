package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.ServiceI.CategoryService;
import com.electronicstore.electronicStoreApp.ServiceI.FileService;
import com.electronicstore.electronicStoreApp.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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

    private Logger logger= LoggerFactory.getLogger(CategoryController.class);


    //create
    /**
     * @param categoryDto
     * @return http status for save data
     * @apiNote This Api is used to create New Categroy in databased
     */
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        logger.info("Entering request for creating new category record");
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        logger.info("Completed request for creating new category record");

        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    //update
    /**
     * @param categoryDto
     * @return http status for Get data
     * @apiNote This Api is used to Update New Category in databased with categoryId
     */
    @PutMapping("/updateCat/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId,@RequestBody CategoryDto categoryDto){
        logger.info("Entering request for updating category record with category id {}:",categoryId);
        CategoryDto update = categoryService.update(categoryDto, categoryId);
        logger.info("Completed request for updating new category record with category id {}:",categoryId);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }

    //delete
    /**
     * *@param categoryDto
     * @return http status for Delete data
     * @apiNote This Api is used to Delete Category in databased
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable String categoryId){
        logger.info("Entering request for deleting category record with category id {}:",categoryId);

        categoryService.delete(categoryId);
        ApiResponse response = ApiResponse.builder().message("Category Deleted Successfully").status(HttpStatus.OK).build();
        logger.info("Completed request for deleting category record with category id {}:",categoryId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    //get all
    /**
     * *@param categoryDto
     * @return http status for Get All data
     * @apiNote This Api is used to Gell All Category in databased
     */
    @GetMapping("/getAll")
    public  ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
        PageableResponse<CategoryDto> all = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info("Completes request for getting category record");
        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    //get single
    /**
     * *@param categoryDto
     * @return http status for Get Single data
     * @apiNote This Api is used to Get Single Category with id in databased
     */
    @GetMapping("/getSingle/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){

        logger.info("Entering request for getting single category record with category id {} :",categoryId);
        CategoryDto categoryDto = categoryService.get(categoryId);
        logger.info("completed request for getting single category record with category id {}:",categoryId);
        return new ResponseEntity<>(categoryDto,HttpStatus.OK);
    }

    /**
     * *@param categoryDto
     * @return http status for image data
     * @apiNote This Api is used to Update Image Category in databased
     */
    @PostMapping("/image/{id}")
    public  ResponseEntity<ImageResponse> uploadingImage(@RequestParam("categoryImage") MultipartFile coverImage, @PathVariable String categoryId) throws IOException {

        logger.info("Entering request for uploading image category record with category id {} :",categoryId);
        String imageName = fileService.upLoadFile(coverImage, imageUploadPath);

        CategoryDto categoryDto = categoryService.get(categoryId);
        categoryDto.setCoverImage(String.valueOf(coverImage));
        CategoryDto update = categoryService.update(categoryDto, categoryId);

        ImageResponse imageResponse=ImageResponse.builder().imgname(imageName).success(true).status(HttpStatus.CREATED).build();
        logger.info("completed request for uploading image category record with category id {} :",categoryId);
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    }

    //server user img
    /**
     * *@param categoryDto
     * @return http status for Get Image data
     * @apiNote This Api is used to Update Image Category in databased
     */
    @GetMapping(value = "/getImage/{id}")
    public void serveUserImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
        logger.info("Entering request for getting image category record with category id {} :",categoryId);
        CategoryDto categoryDto = this.categoryService.get(categoryId);
        InputStream inputStream = fileService.getResource(imageUploadPath, categoryDto.getCoverImage());
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(inputStream,response.getOutputStream());
        logger.info("completed request for getting image category record with category id {}:",categoryId);
    }


}
