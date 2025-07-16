package com.glowmart.shop_management.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(ProductAPI.BASE_PATH)
public class ProductController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping(ProductAPI.PRODUCT_CREATE)
    public ResponseEntity<?> createProduct(@RequestParam("categoryName") String categoryName,
                                           @RequestParam("active") boolean active,
                                           @RequestBody ProductDto productDto){
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
