package com.glowmart.shop_management.converter;

import com.glowmart.shop_management.dto.UserDto;
import com.glowmart.shop_management.entity.User;

/**
 * A utility class for converting between {@link User} and {@link UserDto} objects.
 * <p>
 * This class provides static methods to convert a {@link UserDto} to a {@link User}
 * and vice versa. The conversion maps all relevant fields between the two objects
 * while ensuring consistency in data transformation.
 * </p>
 */
public class UserConverter {

    /**
     * Converts a {@link UserDto} object to a {@link User} entity.
     * <p>
     * This method takes a {@link UserDto} object containing user data and maps it to
     * a {@link User} entity, which can be saved to or retrieved from the database.
     * The conversion includes all user-related fields like username, email, phone number,
     * password, and timestamps.
     * </p>
     *
     * @param userDto the {@link UserDto} object to be converted
     * @return a {@link User} entity populated with the values from the given {@link UserDto}
     */
    public static User convertToUser(UserDto userDto){
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setUserName(userDto.getUserName());
        user.setUserEmail(userDto.getUserEmail());
        user.setUserPhone(userDto.getUserPhone());
        user.setUserPassword(userDto.getUserPassword());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setUpdatedAt(userDto.getUpdatedAt());
        user.setLastLoginTime(userDto.getLastLoginTime());
        return user;
    }

    /**
     * Converts a {@link User} entity to a {@link UserDto} object.
     * <p>
     * This method takes a {@link User} entity and maps its fields to a {@link UserDto}
     * object. The DTO is typically used for transferring user data between different layers
     * of the application, such as the service and presentation layers.
     * </p>
     *
     * @param user the {@link User} entity to be converted
     * @return a {@link UserDto} object populated with the values from the given {@link User}
     */
    public static UserDto convertToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setRoleName(user.getRole().getRoleName());
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
