package com.glowmart.shop_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glowmart.shop_management.api.ProductAPI;
import com.glowmart.shop_management.converter.CategoryConverter;
import com.glowmart.shop_management.converter.UserConverter;
import com.glowmart.shop_management.dto.ProductDto;
import com.glowmart.shop_management.entity.Category;
import com.glowmart.shop_management.entity.User;
import com.glowmart.shop_management.service.CategoryService;
import com.glowmart.shop_management.service.ProductService;
import com.glowmart.shop_management.service.UserService;
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
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = ProductAPI.PRODUCT_CREATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@RequestParam("categoryName") String categoryName,
                                           @RequestParam("active") boolean active,
                                           @RequestParam("product") String productJson,
                                           @RequestParam("image")MultipartFile file) throws IOException {
        // Parse JSON string to DTO
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);

        /*Create uploads directory if not exists*/
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads");
        if (Files.notExists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        /*Save file*/
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        /*Set image path in DB*/
        productDto.setPhotoPath("/images/" + fileName);

        /*Get authenticated user's email from JWT token*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = UserConverter.convertToUser(userService.findUserByEmail(email));
        Category category = CategoryConverter.convertToCategory(categoryService.getCategoryByName(categoryName));
        productDto.setUser(user);
        productDto.setCategory(category);
        productDto.setCreatedAt(LocalDateTime.now());
        productDto.setCreatedBy(user.getUserName());
        productDto.setActive(active);
        productService.createProduct(productDto);
        return new ResponseEntity<>("Product is successfully created.", HttpStatus.CREATED);
    }

}
