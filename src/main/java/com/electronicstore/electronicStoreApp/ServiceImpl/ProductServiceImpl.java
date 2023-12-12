package com.electronicstore.electronicStoreApp.ServiceImpl;

import com.electronicstore.electronicStoreApp.ServiceI.ProductService;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.dto.ProductDto;
import com.electronicstore.electronicStoreApp.entites.Category;
import com.electronicstore.electronicStoreApp.entites.Product;
import com.electronicstore.electronicStoreApp.exception.ResourceNotFoundException;
import com.electronicstore.electronicStoreApp.helper.Helper;
import com.electronicstore.electronicStoreApp.repository.CategoryRepo;
import com.electronicstore.electronicStoreApp.repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper mapper;

    private Logger logger= LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public ProductDto create(ProductDto productDto) {
        logger.info("Initiating dao request for creating product record");
        String id = UUID.randomUUID().toString();
        productDto.setProductId(id);
        Product product = mapper.map(productDto, Product.class);
        Product save = productRepo.save(product);

        logger.info("Completed dao request for creating product record");
        return mapper.map(save,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto,String productId) {
        logger.info("Initiating dao request for updating product record with product id {}:",productId);
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("NOT_FOUND"));
         product.setTitle(productDto.getTitle());
         product.setDescription(productDto.getDescription());
         product.setPrice(productDto.getPrice());
         product.setDiscountedPrice(productDto.getDiscountedPrice());
         product.setQuantity(productDto.getQuantity());
         product.setLive(productDto.isLive());
         product.setStock(productDto.isStock());

        Product updatePro = productRepo.save(product);
        logger.info("Completed dao request for updating product record with product id {} :",productId);
        return mapper.map(updatePro,ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        logger.info("Initiating dao request for deleting product record with product id");
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("NOT_FOUND"));
        logger.info("Completed dao request for deleting product record with product id");

        productRepo.delete(product);
    }

    @Override
    public ProductDto get(String productId) {
        logger.info("Initiating dao request for getting single product record using id : {}");
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException(" NOT_FOUND"));
        logger.info("Completed dao request for getting single product record using id");
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        logger.info("Initiating dao request for getting all category record");
        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepo.findAll(pageable);

        logger.info("Completed dao request for getting all category record");
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
        logger.info("Initiating dao request for getting all live product record");

        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = (Page<Product>) productRepo.findByLiveTrue(pageable);

        logger.info("Completed dao request for getting all live product record");
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {

        logger.info("Initiating dao request for searching by title product record");
        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page =productRepo.findByTitleContaining(subTitle,pageable);

        logger.info("Completed dao request for searching by title product record");
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("NOT_FOUND"));

        Product product = mapper.map(productDto, Product.class);
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product save = productRepo.save(product);

        ProductDto map = mapper.map(save, ProductDto.class);
        return map;
    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {

        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("NOT_FOUND"));
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("NOT_FOUND"));
        product.setCategory(category);
        Product saveProduct = productRepo.save(product);
        return mapper.map(saveProduct, ProductDto.class);
    }

}
