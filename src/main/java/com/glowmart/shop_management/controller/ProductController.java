package com.glowmart.shop_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glowmart.shop_management.api.ProductAPI;
import com.glowmart.shop_management.converter.CategoryConverter;
import com.glowmart.shop_management.converter.UserConverter;
import com.glowmart.shop_management.dto.ProductDto;
import com.glowmart.shop_management.entity.Category;
import com.glowmart.shop_management.entity.User;
import com.glowmart.shop_management.exception.DuplicateException;
import com.glowmart.shop_management.service.CategoryService;
import com.glowmart.shop_management.service.ProductService;
import com.glowmart.shop_management.service.UserService;
import org.apache.commons.collections.functors.ExceptionPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping(ProductAPI.BASE_PATH)
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(value = ProductAPI.PRODUCT_CREATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@RequestParam("categoryName") String categoryName,
                                           @RequestParam("active") boolean active,
                                           @RequestParam("product") String productJson,
                                           @RequestParam("image")MultipartFile file) throws IOException {
        try {
            productService.createProduct(categoryName, active, productJson, file);
            return new ResponseEntity<>("Product is successfully created.", HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DuplicateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
