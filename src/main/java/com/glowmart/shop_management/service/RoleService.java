package com.glowmart.shop_management.service;

import com.glowmart.shop_management.dto.RoleDto;

public interface RoleService {

    RoleDto findRoleByInputRole(String role);

    int countByInputRole(String role);

}
