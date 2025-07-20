package com.glowmart.shop_management.service;

import com.glowmart.shop_management.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

public interface ProductService {

    ProductDto createProduct(String categoryName, boolean active, String productJson, MultipartFile file) throws IOException;
    ProductDto updateProductById(String id, MultipartFile file, String productJson, String categoryName, boolean active) throws IOException;
    ProductDto getProductByName(String name);
    ProductDto deleteProductById(Long id) throws AccessDeniedException;

}
