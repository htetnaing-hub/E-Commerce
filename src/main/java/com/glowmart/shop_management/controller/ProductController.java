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
