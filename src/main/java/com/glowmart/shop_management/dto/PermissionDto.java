package com.glowmart.shop_management.dto;

import com.glowmart.shop_management.entity.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data transfer object (DTO) for a Permission.
 * <p>
 * This DTO is used for transferring permission data between layers of the application. It includes
 * relevant information such as the permission's ID, name, associated roles, and the timestamp for creation.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PermissionDto {

    /**
     * Unique identifier for the permission.
     * <p>
     * This field represents the permission's ID, typically used to reference a specific permission in the system.
     * </p>
     */
    private Long permissionId;

    /**
     * The name of the permission.
     * <p>
     * This field represents the permission's name, which is typically used to define the specific access or action
     * that the permission grants.
     * </p>
     */
    private String permissionName;

    /**
     * The set of roles that have been granted this permission.
     * <p>
     * This field contains the set of roles associated with this permission. Each role can have one or more permissions.
     * </p>
     */
    private Set<Role> roles;

    /**
     * Timestamp for when the permission was created.
     * <p>
     * This field represents the creation date and time of the permission. It is typically set automatically when the
     * permission is created in the system.
     * </p>
     */
    private LocalDateTime createdAt;
}
