package com.glowmart.shop_management.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwt;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {

        /* 1. Grab the bearer token */
        String authHdr = req.getHeader("Authorization");
        if (authHdr == null || !authHdr.startsWith("Bearer ")) {
            chain.doFilter(req, res);               // no header → next filter (might be public)
            return;
        }
        String token = authHdr.substring(7);

        /* 2. Validate signature + expiry */
        String username;
        try {
            username = jwt.extractUsername(token);
            if (jwt.isExpired(token)) {
                chain.doFilter(req, res);           // token expired
                return;
            }
        } catch (Exception ex) {                    // bad signature, malformed, etc.
            chain.doFilter(req, res);
            return;
        }

        /* 3. Convert roles to GrantedAuthority */
        List<GrantedAuthority> auths = jwt.extractRoles(token).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        /* 4. Build Authentication & put in context */
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, null, auths);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

        org.springframework.security.core.context.SecurityContextHolder
                .getContext().setAuthentication(authentication);

        /* 5. Hand off to the next filter */
        chain.doFilter(req, res);
    }
}
