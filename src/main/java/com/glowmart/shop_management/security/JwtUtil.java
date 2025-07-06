package com.glowmart.shop_management.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    /* Do NOT keep secrets in source code in production; use env / vault */
    private static final String SECRET = "12121998hna12121998hna12121998hna"; // ≥ 256‑bit for HS256
    private static final long   EXP_MS = 1_000 * 60 * 60;   // 1 hour

    /** JJWT 0.9.1 can take a byte[] key; we pre‑build it once for safety. */
    private static final Key SIGN_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    /* ---------- 1. BUILD TOKEN -------------------------------------- */

    public String generateToken(UserDetails user) {

        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // ROLE_ADMIN …
                .collect(Collectors.toList());

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXP_MS))
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY)
                .compact();
    }

    /* ---------- 2. READ TOKEN --------------------------------------- */

    public String  extractUsername(String token) { return extract(token, Claims::getSubject); }
    public Date    extractExpiration(String token){ return extract(token, Claims::getExpiration); }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return extract(token, c -> (List<String>) c.get("roles"));
    }

    public <T> T extract(String token, Function<Claims,T> f) {
        return f.apply(parse(token));
    }

    private Claims parse(String token) {
        return Jwts.parser().setSigningKey(SIGN_KEY).parseClaimsJws(token).getBody();
    }

    /* ---------- 3. HELPERS ------------------------------------------ */

    public boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
