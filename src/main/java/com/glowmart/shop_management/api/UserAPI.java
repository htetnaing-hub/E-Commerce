package com.glowmart.shop_management.api;

/**
 * A utility class that contains constants for API endpoint paths related to user operations.
 * <p>
 * This class holds static final strings that define the paths for user-related API endpoints, such as
 * signing up a user, logging in a user, and others. These constants are used across the application to
 * avoid hardcoding the paths and ensure consistency in the API structure.
 * </p>
 */
public final class UserAPI {

    /**
     * The base path for all user-related API endpoints.
     * <p>
     * This is the root URL for the user API endpoints. All user-related paths will be prefixed with
     * this value, such as "/api/user/{role}/sign-up" or "/api/user/login".
     * </p>
     */
    public static final String BASE_PATH = "/api/user";

    /**
     * The path for the user sign-up endpoint.
     * <p>
     * This endpoint allows users to sign up. The path includes a placeholder for the role of the user,
     * which will be dynamically replaced when making the request (e.g., "/api/user/admin/sign-up").
     * </p>
     */
    public static final String USER_SIGN_UP = "/{role}/sign-up";

    /**
     * The path for the user login endpoint.
     * <p>
     * This endpoint allows users to log in. The path is static, as it does not require any dynamic
     * placeholders.
     * </p>
     */
    public static final String USER_LOGIN = "/login";

    /**
     * The path for the refresh token endpoint.
     * <p>
     * This endpoint is used to obtain a new access token by presenting a valid refresh token.
     * It helps maintain user sessions without requiring re‑authentication.
     * </p>
     */
    public static final String REFRESH_TOKEN = "/refresh";

    /**
     * The path for the user logout endpoint.
     * <p>
     * This endpoint invalidates the user's refresh token and ends the session.
     * After logout, the user cannot use the old refresh token to obtain new access tokens.
     * </p>
     */
    public static final String USER_LOGOUT = "/logout";

    /**
     * The path for the user list retrieval endpoint.
     * <p>
     * This endpoint returns a paginated list of users. The path is static and does not include any dynamic
     * parameters or placeholders.
     * </p>
     */
    public static final String USER_LIST = "/user-list";
}
