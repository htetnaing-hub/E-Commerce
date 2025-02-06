package com.glowmart.shop_management.converter;

import com.glowmart.shop_management.dto.RoleDto;
import com.glowmart.shop_management.entity.Role;

/**
 * A utility class for converting between {@link Role} and {@link RoleDto} objects.
 * <p>
 * This class provides static methods to convert a {@link RoleDto} to a {@link Role}
 * and vice versa. The conversion maps all relevant fields between the two objects
 * while ensuring consistency in data transformation.
 * </p>
 */
public class RoleConverter {

    /**
     * Converts a {@link RoleDto} object to a {@link Role} entity.
     * <p>
     * This method takes a {@link RoleDto} object containing role data and maps it to
     * a {@link Role} entity, which can be saved to or retrieved from the database.
     * The conversion includes all role-related fields like role name, associated users,
     * permissions, and timestamps.
     * </p>
     *
     * @param roleDto the {@link RoleDto} object to be converted
     * @return a {@link Role} entity populated with the values from the given {@link RoleDto}
     */
    public static Role convertToRole(RoleDto roleDto){
        Role role = new Role();
        role.setRoleId(roleDto.getRoleId());
        role.setRoleName(roleDto.getRoleName());
        role.setUserLists(roleDto.getUserLists());
        role.setPermissions(roleDto.getPermissions());
        role.setCreatedAt(roleDto.getCreatedAt());
        role.setUpdatedAt(roleDto.getUpdatedAt());
        role.setDeletedAt(roleDto.getDeletedAt());
        return role;
    }

    /**
     * Converts a {@link Role} entity to a {@link RoleDto} object.
     * <p>
     * This method takes a {@link Role} entity and maps its fields to a {@link RoleDto}
     * object. The DTO is typically used for transferring role data between different layers
     * of the application, such as the service and presentation layers.
     * </p>
     *
     * @param role the {@link Role} entity to be converted
     * @return a {@link RoleDto} object populated with the values from the given {@link Role}
     */
    public static RoleDto convertToRoleDto(Role role){
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(role.getRoleId());
        roleDto.setRoleName(role.getRoleName());
        roleDto.setUserLists(role.getUserLists());
        roleDto.setPermissions(role.getPermissions());
        roleDto.setCreatedAt(role.getCreatedAt());
        roleDto.setUpdatedAt(role.getUpdatedAt());
        roleDto.setDeletedAt(role.getDeletedAt());
        return roleDto;
    }

}
