package com.glowmart.shop_management.controller;

import com.glowmart.shop_management.api.CategoryAPI;
import com.glowmart.shop_management.dto.CategoryDto;
import com.glowmart.shop_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CategoryAPI.BASE_PATH)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(CategoryAPI.CATEGORY_CREATE)
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto){
        try{
            CategoryDto createdCategoryDto = categoryService.createCategory(categoryDto);
        } catch (Exception exception){
            return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Category is successfully created.", HttpStatus.CREATED);
    }

}
