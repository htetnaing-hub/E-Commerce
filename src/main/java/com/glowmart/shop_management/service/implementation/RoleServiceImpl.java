package com.glowmart.shop_management.service.implementation;

import com.glowmart.shop_management.converter.RoleConverter;
import com.glowmart.shop_management.dto.RoleDto;
import com.glowmart.shop_management.repository.RoleRepository;
import com.glowmart.shop_management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link RoleService} that provides business logic
 * for managing roles within the application.
 * <p>
 * This service interacts with the {@link RoleRepository} to retrieve
 * and count roles, and uses {@link RoleConverter} to transform entities
 * into DTOs for external use.
 * </p>
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    /**
     * Finds a role by its input string and converts it to a {@link RoleDto}.
     *
     * @param role the input role string to search for
     * @return the corresponding {@link RoleDto}, or {@code null} if no role is found
     */
    @Override
    public RoleDto findRoleByInputRole(String role) {
        return RoleConverter.convertToRoleDto(roleRepository.findRoleByInputRole(role));
    }

    /**
     * Counts the number of roles that match the given input string.
     *
     * @param role the input role string to count
     * @return the number of roles matching the input
     */
    @Override
    public int countByInputRole(String role) {
        return roleRepository.countByInputRole(role);
    }
}
