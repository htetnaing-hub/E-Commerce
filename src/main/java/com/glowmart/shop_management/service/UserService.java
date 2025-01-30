package com.glowmart.shop_management.service;

import com.glowmart.shop_management.dto.UserDto;

public interface UserService {

    UserDto createUser(String role, UserDto userDto);

    UserDto findUserByEmail(String userEmail);

    boolean userExistsByEmail(String userEmail);

}
