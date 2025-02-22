package com.glowmart.shop_management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for the Category entity.
 * <p>
 * This class is used to transfer category-related data between different layers of the application,
 * ensuring that only the necessary details are exposed in API responses.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {

    /**
     * Unique identifier for the category.
     * <p>
     * This field represents the category ID, which is assigned when the category is created.
     * </p>
     */
    private Long categoryId;

    /**
     * The name of the category.
     * <p>
     * This field stores the category's name and is required for category creation.
     * </p>
     *
     * @see jakarta.validation.constraints.NotBlank
     */
    @NotBlank(message = "Category name is required.")
    private String categoryName;

    /**
     * Timestamp indicating when the category was created.
     * <p>
     * This field is required and is automatically set when the category is created.
     * </p>
     *
     * @see jakarta.validation.constraints.NotBlank
     */
    @NotBlank(message = "Created time is required.")
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating when the category was last updated.
     * <p>
     * This field is optional and is updated whenever any changes are made to the category.
     * </p>
     */
    private LocalDateTime updatedAt;
}
