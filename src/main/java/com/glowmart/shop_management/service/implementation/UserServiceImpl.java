package com.glowmart.shop_management.service.implementation;

import com.glowmart.shop_management.converter.UserConverter;
import com.glowmart.shop_management.dto.UserDto;
import com.glowmart.shop_management.entity.Role;
import com.glowmart.shop_management.entity.User;
import com.glowmart.shop_management.exception.NotFoundException;
import com.glowmart.shop_management.service.UserService;
import com.glowmart.shop_management.exception.DuplicateEmailException;
import com.glowmart.shop_management.repository.RoleRepository;
import com.glowmart.shop_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(String role, UserDto userDto) {
        if (role == null) {
            throw new NullPointerException("Role must not be NULL!");
        }
        int countByInputRole = roleRepository.countByInputRole(role);
        if (countByInputRole == 0) {
            throw new NotFoundException("There is no role for '" + role.toUpperCase() + "'. Role must be 'ROLE_USER' or 'ROLE_ADMIN'.");
        }
        if (userRepository.userExistsByEmail(userDto.getUserEmail())) {
            throw new DuplicateEmailException(userDto.getUserEmail() + " is already exists!");
        }
        Role resultRole = roleRepository.findRoleByInputRole(role);
        User user = UserConverter.convertToUser(userDto);
        user.setRole(resultRole);
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        return UserConverter.convertToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto findUserByEmail(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail);
        UserDto userDto = UserConverter.convertToUserDto(user);
        return userDto;
    }

    @Override
    public boolean userExistsByEmail(String userEmail) {
        return userRepository.userExistsByEmail(userEmail);
    }
}
