package com.glowmart.shop_management.security;

import com.glowmart.shop_management.entity.RefreshToken;
import com.glowmart.shop_management.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public void storeToken(String username, String refreshToken) {
        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setUserName(username);
        tokenEntity.setToken(refreshToken);
        tokenEntity.setExpireDate(Instant.now().plus(1, ChronoUnit.DAYS)); // example: 1 days
        refreshTokenRepository.save(tokenEntity);
    }

    @Override
    public boolean validateToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .map(token -> token.getExpireDate().isAfter(Instant.now()))
                .orElse(false);
    }

    @Override
    public void invalidateToken(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
