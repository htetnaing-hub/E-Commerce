package com.glowmart.shop_management.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glowmart.shop_management.common.CommonFunction;
import com.glowmart.shop_management.converter.CategoryConverter;
import com.glowmart.shop_management.converter.ProductConverter;
import com.glowmart.shop_management.converter.UserConverter;
import com.glowmart.shop_management.dto.ProductDto;
import com.glowmart.shop_management.entity.Category;
import com.glowmart.shop_management.entity.Product;
import com.glowmart.shop_management.entity.User;
import com.glowmart.shop_management.exception.DuplicateException;
import com.glowmart.shop_management.exception.NotFoundException;
import com.glowmart.shop_management.exception.NotValidException;
import com.glowmart.shop_management.repository.ProductRepository;
import com.glowmart.shop_management.service.CategoryService;
import com.glowmart.shop_management.service.ProductService;
import com.glowmart.shop_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public ProductDto createProduct(String categoryName, boolean active, String productJson, MultipartFile file) throws IOException {

        // Parse JSON string to DTO
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);

        if (CommonFunction.isValidPriceAndDiscount(productDto.getProductOriginalPrice(),
                productDto.getProductDiscountPercentage()) == false){
            throw new IllegalArgumentException("Product Original Price and Product Discount Percentage are not valid!");
        }
        if(productRepository.existsProductByName(productDto.getProductName().toLowerCase())){
            throw new DuplicateException(productDto.getProductName() + " is already exists!");
        }

        /*Create product photo*/
        String fileName = CommonFunction.createFile(file, productDto);

        /*Get authenticated user's email from JWT token*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        /*Get user by login email*/
        User user = UserConverter.convertToUser(userService.findUserByEmail(email));
        Category category = CategoryConverter.convertToCategory(categoryService.getCategoryByName(categoryName));

        productDto.setProductDiscountAmount(CommonFunction.calculateDiscountAmount(productDto.getProductOriginalPrice(),
                productDto.getProductDiscountPercentage()));
        productDto.setProductFinalPrice(productDto.getProductOriginalPrice() - productDto.getProductDiscountAmount());
        productDto.setProductDescription(productDto.getProductDescription().toLowerCase());
        productDto.setProductName(productDto.getProductName().toLowerCase());
        productDto.setPhotoPath("/images/" + fileName);
        productDto.setCreatedAt(LocalDateTime.now());
        productDto.setCreatedBy(user.getUserName());
        productDto.setCategory(category);
        productDto.setActive(active);
        productDto.setUser(user);

        return ProductConverter.convertToProductDto(productRepository.save(ProductConverter.convertToProduct(productDto)));
    }

    @Override
    public ProductDto updateProductById(String id, MultipartFile file, String productJson, String categoryName, boolean active) throws IOException {
        if(CommonFunction.isValidId(id) == false){
            throw new NotValidException("Product id is not valid! Id must be only number, not null and greater than 0.");
        }
        Long productId = Long.parseLong(id);
        Optional<Product> productById = productRepository.findById(productId);
        String ownerEmail = productById.get().getUser().getUserEmail();

        /*Get authenticated user's email from JWT token*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        if (!loginEmail.equals(ownerEmail)){
            throw new AccessDeniedException("You are not allowed to update another user's product!\nYou can update only your products!");
        }

        // Parse JSON string to DTO
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);
        if (CommonFunction.isValidName(productDto.getProductName()) == false){
            throw new NotValidException("Product name is not valid! Please enter letters and spaces.");
        }

        if(productById.isEmpty()){
            throw new NotFoundException("There is no product by id:" + id + "!");
        }

        Product updateProductById = productById.get();

        if (productDto.getProductName().equalsIgnoreCase(updateProductById.getProductName())){
            throw new DuplicateException(productDto.getProductName().toLowerCase() + " is same with the old category name!");
        }

        if(productRepository.existsProductByName(productDto.getProductName().toLowerCase())){
            throw new DuplicateException(productDto.getProductName().toLowerCase() + " is already exists!");
        }

        /*Create product photo*/
        String fileName = CommonFunction.createFile(file, productDto);

        /*Get user by login email*/
        User user = UserConverter.convertToUser(userService.findUserByEmail(loginEmail));
        Category category = CategoryConverter.convertToCategory(categoryService.getCategoryByName(categoryName));

        /*Calculate discount amount*/
        Double discountAmount = CommonFunction.calculateDiscountAmount(
                productDto.getProductOriginalPrice(),
                productDto.getProductDiscountPercentage()
        );

        updateProductById.setProductOriginalPrice(productDto.getProductOriginalPrice());
        updateProductById.setProductDiscountPercentage(productDto.getProductDiscountPercentage());
        updateProductById.setProductDiscountAmount(discountAmount);
        updateProductById.setProductFinalPrice(productDto.getProductOriginalPrice() - discountAmount);
        updateProductById.setProductDescription(productDto.getProductDescription().toLowerCase());
        updateProductById.setProductName(productDto.getProductName().toLowerCase());
        updateProductById.setPhotoPath("/images/" + fileName);
        updateProductById.setUpdatedAt(LocalDateTime.now());
        updateProductById.setUpdatedBy(user.getUserName());
        updateProductById.setCategory(category);
        updateProductById.setActive(active);
        return ProductConverter.convertToProductDto(productRepository.save(updateProductById));
    }

    @Override
    public ProductDto getProductByName(String name) {
        return null;
    }

    @Override
    public ProductDto deleteProductById(Long id) throws AccessDeniedException {
        Optional<Product> productById = productRepository.findById(id);
        if (productById.isEmpty()){
            throw new NotFoundException("There is no product by id:" + id + "!");
        }
        /*Get authenticated user's email from JWT token*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginEmail = authentication.getName();
        String ownerEmail = productById.get().getUser().getUserEmail();
        if (!loginEmail.equals(ownerEmail)){
            throw new AccessDeniedException("You are not allowed to delete another user's product!\nYou can delete only your products!");
        }
        productRepository.deleteById(id);
        return ProductConverter.convertToProductDto(productById.get());
    }
}
