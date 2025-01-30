package com.glowmart.shop_management.entity.service.implementation;

import com.glowmart.shop_management.converter.RoleConverter;
import com.glowmart.shop_management.dto.RoleDto;
import com.glowmart.shop_management.repository.RoleRepository;
import com.glowmart.shop_management.entity.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleDto findRoleByInputRole(String role) {
        return RoleConverter.convertToRoleDto(roleRepository.findRoleByInputRole(role));
    }

    @Override
    public int countByInputRole(String role) {
        return roleRepository.countByInputRole(role);
    }
}
