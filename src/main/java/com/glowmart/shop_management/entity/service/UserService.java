package com.glowmart.shop_management.entity.service;

import com.glowmart.shop_management.dto.UserDto;

import java.util.Optional;

public interface UserService {

    UserDto createUser(String role, UserDto userDto);

    UserDto findUserByEmail(String userEmail);

    boolean userExistsByEmail(String userEmail);

}
