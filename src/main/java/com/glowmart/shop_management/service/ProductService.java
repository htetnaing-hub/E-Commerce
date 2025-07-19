package com.glowmart.shop_management.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.glowmart.shop_management.dto.CategoryDto;
import com.glowmart.shop_management.dto.ProductDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    ProductDto createProduct(String categoryName, boolean active, String productJson, MultipartFile file) throws IOException;
    ProductDto getProductByName(String name);

}
