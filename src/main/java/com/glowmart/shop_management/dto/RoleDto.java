package com.glowmart.shop_management.dto;

import com.glowmart.shop_management.entity.Permission;
import com.glowmart.shop_management.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Data transfer object (DTO) for a Role.
 * <p>
 * This DTO is used for transferring role data between layers of the application. It includes
 * relevant information such as the role's ID, name, associated users, permissions, and timestamps
 * for creation, updates, and deletion. It does not contain sensitive information like user passwords.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleDto {

    /**
     * Unique identifier for the role.
     * <p>
     * This field represents the role's ID, typically used to reference a specific role in the system.
     * </p>
     */
    private Long roleId;

    /**
     * The name of the role.
     * <p>
     * This field represents the role's name, typically used to define the type of access or permission
     * the role provides within the system.
     * </p>
     */
    private String roleName;

    /**
     * The list of users assigned to this role.
     * <p>
     * This field contains a set of users who have been assigned to the current role. A role can be assigned
     * to multiple users, and a user can only have one role.
     * </p>
     */
    private Set<User> userLists;


    /**
     * The set of permissions associated with this role.
     * <p>
     * This field contains the set of permissions granted to the role. Each permission allows the role to perform
     * specific actions in the system. A role can have multiple permissions.
     * </p>
     */
    private Set<Permission> permissions;

    /**
     * Timestamp for when the role was created.
     * <p>
     * This field represents the creation date and time of the role. It is typically set automatically when the
     * role is created in the system.
     * </p>
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp for the last update to the role.
     * <p>
     * This field represents the last update date and time of the role. It may be updated when the role's name,
     * permissions, or associated users change.
     * </p>
     */
    private LocalDateTime updatedAt;

    /**
     * Timestamp for when the role was deleted.
     * <p>
     * This field tracks the deletion time of the role. It is typically used for soft deletion, indicating when
     * the role was logically removed from the system. If null, the role is active.
     * </p>
     */
    private LocalDateTime deletedAt;
}
