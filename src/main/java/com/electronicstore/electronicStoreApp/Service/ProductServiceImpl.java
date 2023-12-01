package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.dto.ProductDto;
import com.electronicstore.electronicStoreApp.entites.Product;
import com.electronicstore.electronicStoreApp.exception.ResourceNotFoundException;
import com.electronicstore.electronicStoreApp.helper.Helper;
import com.electronicstore.electronicStoreApp.repository.ProductRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper mapper;
    @Override
    public ProductDto create(ProductDto productDto) {
        String id = UUID.randomUUID().toString();
        productDto.setProductId(id);
        Product product = mapper.map(productDto, Product.class);
        Product save = productRepo.save(product);
        return mapper.map(save,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto,String productId) {

        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found !!"));
         product.setTitle(productDto.getTitle());
         product.setDescription(productDto.getDescription());
         product.setPrice(productDto.getPrice());
         product.setDiscountedPrice(productDto.getDiscountedPrice());
         product.setQuantity(productDto.getQuantity());
         product.setLive(productDto.isLive());
         product.setStock(productDto.isStock());

        Product updatePro = productRepo.save(product);
        return mapper.map(updatePro,ProductDto.class);
    }

    @Override
    public void delete(String productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found !!"));
        productRepo.delete(product);
    }

    @Override
    public ProductDto get(String productId) {
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found !!"));
        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepo.findAll(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = (Page<Product>) productRepo.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page =productRepo.findByTitleContaining(subTitle,pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }
}
