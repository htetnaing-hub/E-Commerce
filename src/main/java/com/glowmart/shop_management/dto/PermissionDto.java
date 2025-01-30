package com.glowmart.shop_management.dto;

import com.glowmart.shop_management.entity.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PermissionDto {
    private Long permissionId;
    private String permissionName;
    private Set<Role> roles;
    private LocalDateTime createdAt;
}
