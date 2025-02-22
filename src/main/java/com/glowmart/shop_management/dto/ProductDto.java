package com.glowmart.shop_management.dto;

import com.glowmart.shop_management.entity.Category;
import com.glowmart.shop_management.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for the Product entity.
 * <p>
 * This class is used to transfer product-related data between different layers of the application,
 * ensuring that only the necessary details are exposed in API responses.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {

    /**
     * Unique identifier for the product.
     * <p>
     * This field represents the product ID, which is assigned when the product is created.
     * </p>
     */
    private Long productId;

    /**
     * The user who created the product.
     * <p>
     * This field establishes a relationship between the product and the user who added it.
     * </p>
     */
    private User user;

    /**
     * The category associated with the product.
     * <p>
     * This field determines the classification of the product.
     * </p>
     */
    private Category category;

    /**
     * The name of the product.
     * <p>
     * This field is required and must not be empty.
     * </p>
     *
     * @see jakarta.validation.constraints.NotBlank
     */
    @NotBlank(message = "Product name is required.")
    private String productName;

    /**
     * The actual price of the product.
     * <p>
     * This field is required and must be a positive value.
     * </p>
     *
     * @see jakarta.validation.constraints.Min
     */
    @NotBlank(message = "Product real price is required.")
    private double productRealPrice;

    /**
     * A brief description of the product.
     * <p>
     * This field provides details about the product and is required.
     * </p>
     */
    @NotBlank(message = "Product description is required.")
    private String productDescription;

    /**
     * The discount type applied to the product.
     * <p>
     * This could be a percentage-based discount or a fixed amount.
     * </p>
     */
    @NotBlank(message = "Product discount type is required.")
    private String productDiscountType;

    /**
     * The discount amount applied to the product.
     * <p>
     * This field is required and must be a non-negative value.
     * </p>
     *
     * @see jakarta.validation.constraints.Min
     */
    @NotBlank(message = "Product discount amount is required.")
    @Min(value = 0, message = "Product discount amount must be at least 0")
    private double productDiscountAmount;

    /**
     * Timestamp indicating when the product was created.
     * <p>
     * This field is required and is set when the product is added.
     * </p>
     */
    @NotBlank(message = "Created time is required.")
    private LocalDateTime createdAt;

    /**
     * The username of the person who created the product.
     * <p>
     * This field is required for auditing purposes.
     * </p>
     */
    @NotBlank(message = "Created user name is required.")
    private String createdBy;

    /**
     * Timestamp indicating when the product was last updated.
     * <p>
     * This field is optional and is updated whenever any changes are made to the product.
     * </p>
     */
    private LocalDateTime updatedAt;

    /**
     * The username of the person who last updated the product.
     * <p>
     * This field is optional and is used for auditing.
     * </p>
     */
    private String updatedBy;

    /**
     * Timestamp indicating when the product was deleted.
     * <p>
     * This field is optional and is only set if the product has been marked as deleted.
     * </p>
     */
    private LocalDateTime deletedAt;

    /**
     * The username of the person who deleted the product.
     * <p>
     * This field is optional and is used for auditing.
     * </p>
     */
    private String deletedBy;

    /**
     * The active status of the product.
     * <p>
     * This field indicates whether the product is currently available (true) or inactive (false).
     * </p>
     */
    @NotBlank(message = "Active or Inactive boolean value is required.")
    private boolean active;

    /**
     * The final price of the product after applying discounts.
     * <p>
     * This field represents the effective price after any discounts are applied.
     * </p>
     */
    @NotBlank(message = "Product discount price is required.")
    private double productDiscountPrice;

    /**
     * The discount percentage applied to the product.
     * <p>
     * This field represents the percentage of the discount applied.
     * </p>
     */
    @NotBlank(message = "Product discount percentage is required.")
    private int productDiscountPercentage = 0;
}
