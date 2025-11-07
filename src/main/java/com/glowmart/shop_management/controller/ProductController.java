package com.glowmart.shop_management.controller;

import com.glowmart.shop_management.api.ProductAPI;
import com.glowmart.shop_management.dto.ProductDto;
import com.glowmart.shop_management.exception.DuplicateException;
import com.glowmart.shop_management.exception.NotFoundException;
import com.glowmart.shop_management.exception.NotValidException;
import com.glowmart.shop_management.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * REST controller for managing product operations.
 * <p>
 * This controller provides endpoints for creating, updating, deleting, and retrieving products.
 * It delegates business logic to the {@link ProductService} and handles exceptions by returning
 * appropriate HTTP status codes.
 * </p>
 *
 * <h3>Available Endpoints:</h3>
 * <ul>
 *   <li><b>POST</b> {@code /api/product/create} – Create a new product with details and image.</li>
 *   <li><b>PUT</b> {@code /api/product/update/{id}} – Update an existing product by ID.</li>
 *   <li><b>DELETE</b> {@code /api/product/delete/{id}} – Delete a product by ID.</li>
 *   <li><b>GET</b> {@code /api/product/get-by-id} – Retrieve a product by its ID.</li>
 * </ul>
 *
 * <p>
 * Exception handling is performed at the controller level, returning appropriate
 * HTTP status codes such as {@code 400 Bad Request}, {@code 404 Not Found},
 * {@code 409 Conflict}, or {@code 503 Service Unavailable} depending on the error.
 * </p>
 */
@RestController
@RequestMapping(ProductAPI.BASE_PATH)
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * Create a new product.
     * <p>
     * Accepts product details and an image file via multipart form data. The product is associated
     * with a category and marked as active or inactive. Returns a success message if creation
     * is successful, or an error message with the appropriate HTTP status if validation fails
     * or a duplicate product exists.
     * </p>
     *
     * @param categoryName the name of the category to which the product belongs
     * @param active       whether the product is active
     * @param productJson  the product details in JSON format
     * @param file         the image file associated with the product
     * @return a response entity containing a success or error message
     * @throws IOException if an error occurs while processing the image file
     */
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

    /**
     * Update an existing product by its ID.
     * <p>
     * Accepts a product ID as a path variable, product details in JSON format, and an image file.
     * Returns a success message if the update is successful, or an error message with the appropriate
     * HTTP status if validation fails, the product is not found, or a duplicate exists.
     * </p>
     *
     * @param id           the ID of the product to update
     * @param categoryName the name of the category to which the product belongs
     * @param active       whether the product is active
     * @param file         the new image file for the product
     * @param productJson  the updated product details in JSON format
     * @return a response entity containing a success or error message
     * @throws IOException if an error occurs while processing the image file
     */
    @PutMapping(ProductAPI.PRODUCT_UPDATE)
    public ResponseEntity<?> updateProductById(@PathVariable("id") String id,
                                               @RequestParam("categoryName") String categoryName,
                                               @RequestParam("active") boolean active,
                                               @RequestParam("image") MultipartFile file,
                                               @RequestParam("product") String productJson) throws IOException {
        try {
            productService.updateProductById(id, file, productJson, categoryName, active);
            return new ResponseEntity<>("Product is successfully updated.", HttpStatus.OK);
        } catch (NotValidException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DuplicateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /**
     * Delete a product by its ID.
     * <p>
     * Accepts a product ID as a path variable and deletes the corresponding product.
     * Returns a success message if deletion is successful, or an error message with
     * the appropriate HTTP status if the product is not found.
     * </p>
     *
     * @param id the ID of the product to delete
     * @return a response entity containing a success or error message
     */
    @DeleteMapping(ProductAPI.PRODUCT_DELETE)
    public ResponseEntity<?> deleteProductById(@PathVariable("id") Long id){
        try {
            ProductDto deletedProduct = productService.deleteProductById(id);
            return new ResponseEntity<>(deletedProduct.getProductName() + " is successfully deleted from Product.", HttpStatus.OK);
        } catch (NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /**
     * Retrieve a product by its ID.
     * <p>
     * Accepts a product ID as a request parameter and returns the corresponding product details.
     * Returns an error message with the appropriate HTTP status if validation fails or the product
     * is not found.
     * </p>
     *
     * @param id the ID of the product to retrieve
     * @return a response entity containing the product details or an error message
     */
    @GetMapping(ProductAPI.PRODUCT_BY_ID)
    public ResponseEntity<?> getProductById(@RequestParam("id") String id){
        try {
            ProductDto productDto = productService.getProductById(id);
            return ResponseEntity.ok(productDto);
        } catch (NotValidException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
