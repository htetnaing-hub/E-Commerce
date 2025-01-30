package com.glowmart.shop_management.converter;

import com.glowmart.shop_management.dto.UserDto;
import com.glowmart.shop_management.entity.User;

public class UserConverter {

    public static User convertToUser(UserDto userDto){
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setRole(userDto.getRole());
        user.setUserName(userDto.getUserName());
        user.setUserEmail(userDto.getUserEmail());
        user.setUserPhone(userDto.getUserPhone());
        user.setUserPassword(userDto.getUserPassword());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setUpdatedAt(userDto.getUpdatedAt());
        user.setLastLoginTime(userDto.getLastLoginTime());
        return user;
    }

    public static UserDto convertToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setRole(user.getRole());
        userDto.setUserName(user.getUserName());
        userDto.setUserEmail(user.getUserEmail());
        userDto.setUserPhone(user.getUserPhone());
        userDto.setUserPassword(user.getUserPassword());
        userDto.setCreatedAt(user.getCreatedAt());
        userDto.setUpdatedAt(user.getUpdatedAt());
        userDto.setLastLoginTime(user.getLastLoginTime());
        return userDto;
    }

}
