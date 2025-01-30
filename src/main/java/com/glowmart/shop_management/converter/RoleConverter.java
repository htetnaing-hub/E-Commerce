package com.glowmart.shop_management.converter;

import com.glowmart.shop_management.dto.RoleDto;
import com.glowmart.shop_management.entity.Role;

public class RoleConverter {

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
