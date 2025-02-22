package com.glowmart.shop_management.converter;

import com.glowmart.shop_management.dto.CategoryDto;
import com.glowmart.shop_management.entity.Category;

/**
 * Utility class for converting between {@link Category} and {@link CategoryDto}.
 */
public class CategoryConverter {

    /**
     * Converts a {@link CategoryDto} object to a {@link Category} entity.
     *
     * @param categoryDto The DTO to be converted.
     * @return The corresponding {@link Category} entity.
     */
    public static Category convertToCategory(CategoryDto categoryDto){
        return new Category(categoryDto.getCategoryId(),
                categoryDto.getCategoryName(),
                categoryDto.getCreatedAt(),
                categoryDto.getUpdatedAt());
    }

    /**
     * Converts a {@link Category} entity to a {@link CategoryDto}.
     *
     * @param category The entity to be converted.
     * @return The corresponding {@link CategoryDto}.
     */
    public static CategoryDto convertToCategoryDto(Category category){
        return new CategoryDto(category.getCategoryId(),
                category.getCategoryName(),
                category.getCreatedAt(),
                category.getUpdatedAt());
    }

}
