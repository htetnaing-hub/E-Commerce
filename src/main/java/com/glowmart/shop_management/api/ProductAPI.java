package com.glowmart.shop_management.api;

/**
 * A utility class that contains constants for API endpoint paths related to product operations.
 * <p>
 * This class defines static final strings representing the paths for product-related API endpoints,
 * such as creating, updating, deleting, and retrieving products. These constants ensure consistency
 * across the application and prevent hardcoding of endpoint paths.
 * </p>
 */
public final class ProductAPI {

    /**
     * The base path for all product-related API endpoints.
     * <p>
     * All product endpoints are prefixed with this value (e.g., "/api/product/create").
     * </p>
     */
    public static final String BASE_PATH = "/api/product";

    /**
     * The path for creating a new product.
     * <p>
     * This endpoint accepts product details in the request body and persists them.
     * </p>
     */
    public static final String PRODUCT_CREATE = "/create";

    /**
     * The path for updating an existing product.
     * <p>
     * This endpoint requires the product ID as a path variable (e.g., "/api/product/update/10").
     * </p>
     */
    public static final String PRODUCT_UPDATE = "/update/{id}";

    /**
     * The path for deleting a product.
     * <p>
     * This endpoint requires the product ID as a path variable (e.g., "/api/product/delete/10").
     * </p>
     */
    public static final String PRODUCT_DELETE = "/delete/{id}";

    /**
     * The path for retrieving a product by its unique identifier.
     * <p>
     * This endpoint requires a product ID parameter to fetch the corresponding product.
     * </p>
     */
    public static final String PRODUCT_BY_ID = "/get-by-id";

}
