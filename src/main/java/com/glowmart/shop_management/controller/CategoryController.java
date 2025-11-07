package com.glowmart.shop_management.controller;

import com.glowmart.shop_management.api.CategoryAPI;
import com.glowmart.shop_management.dto.CategoryDto;
import com.glowmart.shop_management.exception.DuplicateException;
import com.glowmart.shop_management.exception.NotFoundException;
import com.glowmart.shop_management.exception.NotValidException;
import com.glowmart.shop_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing category operations.
 * <p>
 * This controller provides endpoints for creating, updating, deleting, and retrieving categories.
 * It delegates business logic to the {@link CategoryService} and handles exceptions by returning
 * appropriate HTTP status codes.
 * </p>
 *
 * <h3>Available Endpoints:</h3>
 * <ul>
 *   <li><b>POST</b> {@code /api/category/create} – Create a new category.</li>
 *   <li><b>PUT</b> {@code /api/category/update/{id}} – Update an existing category by ID.</li>
 *   <li><b>DELETE</b> {@code /api/category/delete/{id}} – Delete a category by ID.</li>
 *   <li><b>GET</b> {@code /api/category/list} – Retrieve all categories.</li>
 *   <li><b>GET</b> {@code /api/category/get-by-id} – Retrieve a category by its ID.</li>
 *   <li><b>GET</b> {@code /api/category/get-by-name} – Retrieve a category by its name.</li>
 * </ul>
 *
 * <p>
 * Exception handling is performed at the controller level, returning appropriate
 * HTTP status codes such as {@code 400 Bad Request}, {@code 404 Not Found},
 * {@code 409 Conflict}, or {@code 503 Service Unavailable} depending on the error.
 * </p>
 */
@RestController
@RequestMapping(CategoryAPI.BASE_PATH)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Create a new category.
     * <p>
     * Accepts a {@link CategoryDto} in the request body and attempts to persist it.
     * Returns a success message if creation is successful, or an error message with
     * the appropriate HTTP status if validation fails or a duplicate exists.
     * </p>
     *
     * @param categoryDto the category data to create
     * @return a response entity containing a success or error message
     */
    @PostMapping(CategoryAPI.CATEGORY_CREATE)
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto){
        try{
            CategoryDto createdCategoryDto = categoryService.createCategory(categoryDto);
            return new ResponseEntity<>("Category is successfully created.", HttpStatus.CREATED);
        } catch (NotValidException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DuplicateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /**
     * Update an existing category by its ID.
     * <p>
     * Accepts a category ID as a path variable and a {@link CategoryDto} in the request body.
     * Returns a success message if the update is successful, or an error message with
     * the appropriate HTTP status if validation fails, the category is not found, or a duplicate exists.
     * </p>
     *
     * @param id          the ID of the category to update
     * @param categoryDto the updated category data
     * @return a response entity containing a success or error message
     */
    @PutMapping(CategoryAPI.CATEGORY_UPDATE)
    public ResponseEntity<?> updateCategoryById(@PathVariable("id") String id, @RequestBody CategoryDto categoryDto){
        try{
            CategoryDto updateCategoryById = categoryService.updateCategoryById(id, categoryDto);
            return new ResponseEntity<>("Category is successfully updated.", HttpStatus.OK);
        } catch (NotValidException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DuplicateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete a category by its ID.
     * <p>
     * Accepts a category ID as a path variable and deletes the corresponding category.
     * Returns a success message if deletion is successful, or an error message with
     * the appropriate HTTP status if validation fails or the category is not found.
     * </p>
     *
     * @param id the ID of the category to delete
     * @return a response entity containing a success or error message
     */
    @DeleteMapping(CategoryAPI.CATEGORY_DELETE)
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id") String id){
        try {
            CategoryDto deleteCategoryById = categoryService.deleteCategoryById(id);
            return new ResponseEntity<>(deleteCategoryById.getCategoryName() + " is successfully deleted from Category.",
                    HttpStatus.OK);
        } catch (NotValidException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieve all categories.
     * <p>
     * Returns a list of all categories in the system.
     * </p>
     *
     * @return a response entity containing the list of categories or an error message
     */
    @GetMapping(CategoryAPI.CATEGORY_LIST)
    public ResponseEntity<?> getAllCategory(){
        try {
            List<CategoryDto> allCategory = categoryService.getAllCategory();
            return ResponseEntity.ok(allCategory);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /**
     * Retrieve a category by its ID.
     * <p>
     * Accepts a category ID as a request parameter and returns the corresponding category.
     * Returns an error message with the appropriate HTTP status if validation fails or the category is not found.
     * </p>
     *
     * @param id the ID of the category to retrieve
     * @return a response entity containing the category or an error message
     */
    @GetMapping(CategoryAPI.CATEGORY_BY_ID)
    public ResponseEntity<?> getCategoryById(@RequestParam("id") String id){
        try {
            CategoryDto categoryById =categoryService.getCategoryById(id);
            return ResponseEntity.ok(categoryById);
        } catch (NotValidException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /**
     * Retrieve a category by its name.
     * <p>
     * Accepts a category name as a request parameter and returns the corresponding category.
     * Returns an error message with the appropriate HTTP status if validation fails or the category is not found.
     * </p>
     *
     * @param name the name of the category to retrieve
     * @return a response entity containing the category or an error message
     */
    @GetMapping(CategoryAPI.CATEGORY_BY_NAME)
    public ResponseEntity<?> getCategoryByName(@RequestParam("name") String name){
        try {
            CategoryDto categoryByName = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(categoryByName);
        } catch (NotValidException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

}
