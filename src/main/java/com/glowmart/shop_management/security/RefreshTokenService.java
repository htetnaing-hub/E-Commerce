package com.glowmart.shop_management.security;

public interface RefreshTokenService {
    void storeToken(String username, String refreshToken);
    boolean validateToken(String refreshToken);
    void invalidateToken(String refreshToken);
}
