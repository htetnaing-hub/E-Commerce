package com.glowmart.shop_management.dto;

import com.glowmart.shop_management.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Long userId;

    private Role role;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be at lease 2 characters")
    private String userName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String userEmail;

    @NotBlank(message = "Phone number is required")
    private String userPhone;

    @NotBlank(message = "Password is required")
    private String userPassword;

    /*@Column(nullable = false)
    private UserAddress address;*/

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastLoginTime;
}
