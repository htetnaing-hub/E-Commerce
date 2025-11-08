package com.glowmart.shop_management.security;

import com.glowmart.shop_management.api.CategoryAPI;
import com.glowmart.shop_management.api.ProductAPI;
import com.glowmart.shop_management.api.UserAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService,
                          JwtAuthenticationFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration cfg)
            throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(UserAPI.BASE_PATH + UserAPI.USER_SIGN_UP,
                                UserAPI.BASE_PATH + UserAPI.USER_LOGIN,
                                UserAPI.BASE_PATH + UserAPI.REFRESH_TOKEN,
                                UserAPI.BASE_PATH + UserAPI.USER_LOGOUT,
                                UserAPI.BASE_PATH + UserAPI.USER_UPDATE,
                                CategoryAPI.BASE_PATH + CategoryAPI.CATEGORY_LIST,
                                ProductAPI.BASE_PATH + ProductAPI.PRODUCT_CREATE,
                                ProductAPI.BASE_PATH + ProductAPI.PRODUCT_UPDATE,
                                ProductAPI.BASE_PATH + ProductAPI.PRODUCT_DELETE,
                                ProductAPI.BASE_PATH + ProductAPI.PRODUCT_BY_ID).permitAll()
                        .requestMatchers(UserAPI.BASE_PATH + UserAPI.USER_LIST,
                                CategoryAPI.BASE_PATH + CategoryAPI.CATEGORY_CREATE,
                                CategoryAPI.BASE_PATH + CategoryAPI.CATEGORY_UPDATE,
                                CategoryAPI.BASE_PATH + CategoryAPI.CATEGORY_DELETE,
                                CategoryAPI.BASE_PATH + CategoryAPI.CATEGORY_BY_ID,
                                CategoryAPI.BASE_PATH + CategoryAPI.CATEGORY_BY_NAME).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
