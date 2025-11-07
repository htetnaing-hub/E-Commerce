package com.glowmart.shop_management.api;

/**
 * A utility class that contains constants for API endpoint paths related to category operations.
 * <p>
 * This class defines static final strings representing the paths for category-related API endpoints,
 * such as listing categories, retrieving categories by specific attributes, creating new categories,
 * updating existing ones, and deleting categories. These constants ensure consistency across the
 * application and prevent hardcoding of endpoint paths.
 * </p>
 */
public final class CategoryAPI {

    /**
     * The base path for all category-related API endpoints.
     * <p>
     * All category endpoints are prefixed with this value (e.g., "/api/category/list").
     * </p>
     */
    public static final String BASE_PATH = "/api/category";

    /**
     * The path for retrieving a list of all categories.
     * <p>
     * This endpoint returns a collection of categories, typically in a paginated format.
     * </p>
     */
    public static final String CATEGORY_LIST = "/list";

    /**
     * The path for retrieving a category by its unique identifier.
     * <p>
     * This endpoint requires a category ID parameter to fetch the corresponding category.
     * </p>
     */
    public static final String CATEGORY_BY_ID = "/get-by-id";

    /**
     * The path for retrieving a category by its name.
     * <p>
     * This endpoint allows searching for categories using their name.
     * </p>
     */
    public static final String CATEGORY_BY_NAME = "/get-by-name";

    /**
     * The path for retrieving categories by their creation date.
     * <p>
     * This endpoint filters categories based on when they were created.
     * </p>
     */
    public static final String CATEGORY_BY_CREATED_AT = "/get-by-created-at";

    /**
     * The path for retrieving categories by their last updated date.
     * <p>
     * This endpoint filters categories based on their most recent update timestamp.
     * </p>
     */
    public static final String CATEGORY_BY_UPDATED_AT = "/get-by-updated-at";

    /**
     * The path for creating a new category.
     * <p>
     * This endpoint accepts category details in the request body and persists them.
     * </p>
     */
    public static final String CATEGORY_CREATE = "/create";

    /**
     * The path for updating an existing category.
     * <p>
     * This endpoint requires the category ID as a path variable (e.g., "/api/category/update/5").
     * </p>
     */
    public static final String CATEGORY_UPDATE = "/update/{id}";

    /**
     * The path for deleting a category.
     * <p>
     * This endpoint requires the category ID as a path variable (e.g., "/api/category/delete/5").
     * </p>
     */
    public static final String CATEGORY_DELETE = "/delete/{id}";

}
