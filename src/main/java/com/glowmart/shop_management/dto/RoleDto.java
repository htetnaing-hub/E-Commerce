package com.glowmart.shop_management.dto;

import com.glowmart.shop_management.entity.Permission;
import com.glowmart.shop_management.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleDto {
    private Long roleId;
    private String roleName;
    private Set<User> userLists;
    private Set<Permission> permissions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
