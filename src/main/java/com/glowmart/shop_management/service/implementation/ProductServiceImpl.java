package com.glowmart.shop_management.service.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glowmart.shop_management.common.CommonFunction;
import com.glowmart.shop_management.converter.CategoryConverter;
import com.glowmart.shop_management.converter.ProductConverter;
import com.glowmart.shop_management.converter.UserConverter;
import com.glowmart.shop_management.dto.ProductDto;
import com.glowmart.shop_management.entity.Category;
import com.glowmart.shop_management.entity.User;
import com.glowmart.shop_management.exception.DuplicateException;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
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

        productDto.setProductDiscountAmount(CommonFunction.calculateDiscountAmount(productDto.getProductOriginalPrice(),
                productDto.getProductDiscountPercentage()));
        productDto.setProductFinalPrice(productDto.getProductOriginalPrice() - productDto.getProductDiscountAmount());
        productDto.setProductDescription(productDto.getProductDescription().toLowerCase());
        productDto.setProductName(productDto.getProductName().toLowerCase());
        productDto.setCreatedAt(LocalDateTime.now());
        productDto.setCreatedBy(user.getUserName());
        productDto.setCategory(category);
        productDto.setActive(active);
        productDto.setUser(user);

        return ProductConverter.convertToProductDto(productRepository.save(ProductConverter.convertToProduct(productDto)));
    }

    @Override
    public ProductDto getProductByName(String name) {
        return null;
    }
}
