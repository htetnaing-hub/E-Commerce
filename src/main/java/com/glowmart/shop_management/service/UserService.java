package com.glowmart.shop_management.service;

import com.glowmart.shop_management.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserDto createUser(String role, UserDto userDto);

    UserDto findUserByEmail(String userEmail);

    boolean userExistsByEmail(String userEmail);

    List<UserDto> findUsersAfterId(Long lastId, int size);
}
