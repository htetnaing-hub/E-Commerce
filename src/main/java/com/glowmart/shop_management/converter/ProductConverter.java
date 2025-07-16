package com.glowmart.shop_management.converter;

import com.glowmart.shop_management.dto.ProductDto;
import com.glowmart.shop_management.entity.Product;

/**
 * Utility class for converting between {@link Product} and {@link ProductDto}.
 */
public class ProductConverter {

    /**
     * Converts a {@link ProductDto} object to a {@link Product} entity.
     *
     * @param productDto The DTO to be converted.
     * @return The corresponding {@link Product} entity.
     */
    public static Product convertToProduct(ProductDto productDto){
        return new Product(productDto.getProductId(),
                productDto.getUser(),
                productDto.getCategory(),
                //productDto.getWishLists(),
                productDto.getProductName(),
                productDto.getProductOriginalPrice(),
                productDto.getProductDescription(),
                productDto.getProductDiscountAmount(),
                productDto.getCreatedAt(),
                productDto.getCreatedBy(),
                productDto.getUpdatedAt(),
                productDto.getUpdatedBy(),
                productDto.getDeletedAt(),
                productDto.getDeletedBy(),
                productDto.isActive(),
                productDto.getProductFinalPrice(),
                productDto.getProductDiscountPercentage());
    }

    /**
     * Converts a {@link Product} entity to a {@link ProductDto}.
     *
     * @param product The entity to be converted.
     * @return The corresponding {@link ProductDto}.
     */
    public static ProductDto convertToProductDto(Product product){
        return new ProductDto(product.getProductId(),
                product.getUser(),
                product.getCategory(),
                //product.getWishLists(),
                product.getProductName(),
                product.getProductOriginalPrice(),
                product.getProductDescription(),
                product.getProductDiscountAmount(),
                product.getCreatedAt(),
                product.getCreatedBy(),
                product.getUpdatedAt(),
                product.getUpdatedBy(),
                product.getDeletedAt(),
                product.getDeletedBy(),
                product.isActive(),
                product.getProductFinalPrice(),
                product.getProductDiscountPercentage());
    }

}
