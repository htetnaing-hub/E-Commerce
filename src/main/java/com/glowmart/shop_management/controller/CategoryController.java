package com.glowmart.shop_management.controller;

import com.glowmart.shop_management.api.CategoryAPI;
import com.glowmart.shop_management.dto.CategoryDto;
import com.glowmart.shop_management.exception.NotFoundException;
import com.glowmart.shop_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CategoryAPI.BASE_PATH)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(CategoryAPI.CATEGORY_CREATE)
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto){
        try{
            CategoryDto createdCategoryDto = categoryService.createCategory(categoryDto);
            return new ResponseEntity<>("Category is successfully created.", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(CategoryAPI.CATEGORY_UPDATE)
    public ResponseEntity<?> updateCategoryById(@PathVariable("id") Long id, @RequestBody CategoryDto categoryDto){
        try{
            CategoryDto updateCategoryById = categoryService.updateCategoryById(id, categoryDto);
            return new ResponseEntity<>("Category is successfully updated.", HttpStatus.OK);
        } catch(Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(CategoryAPI.CATEGORY_DELETE)
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") Long id){
        CategoryDto deleteCategoryById = categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(deleteCategoryById.getCategoryName() + " is successfully deleted from Category.",
                HttpStatus.OK);
    }

    @GetMapping(CategoryAPI.CATEGORY_LIST)
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        return ResponseEntity.ok(allCategory);
    }

    @GetMapping(CategoryAPI.CATEGORY_BY_ID)
    public ResponseEntity<?> getCategoryById(@RequestParam("id") Long id){
        try {
            CategoryDto categoryById =categoryService.getCategoryById(id);
            return ResponseEntity.ok(categoryById);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(CategoryAPI.CATEGORY_BY_NAME)
    public ResponseEntity<?> getCategoryByName(@RequestParam("name") String name){
        try {
            CategoryDto categoryByName = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(categoryByName);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
