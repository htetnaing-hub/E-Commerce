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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserService} that provides business logic
 * for managing {@link User} entities.
 * <p>
 * This service handles user creation, login time updates, retrieval by email,
 * existence checks, and paginated queries. It interacts with the
 * {@link UserRepository} and {@link RoleRepository} for persistence,
 * and uses {@link UserConverter} to transform entities into DTOs.
 * </p>
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Creates a new user with the specified role.
     * <p>
     * Validates the role, checks for duplicate emails, encodes the user's password,
     * and assigns the appropriate {@link Role} before saving.
     * </p>
     *
     * @param role the role to assign to the user (e.g., "ROLE_USER" or "ROLE_ADMIN")
     * @param userDto the DTO containing user details
     * @return the created {@link UserDto}
     * @throws NullPointerException if the role is {@code null}
     * @throws NotFoundException if the specified role does not exist
     * @throws DuplicateEmailException if a user with the same email already exists
     */
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

    /**
     * Updates the login time of a user identified by their email.
     *
     * @param email the email of the user whose login time should be updated
     */
    @Override
    @Transactional
    public void updateLoginTime(String email) {
        userRepository.updateLoginTime(email);
    }

    /**
     * Finds a user by their email address.
     *
     * @param userEmail the email of the user to find
     * @return the corresponding {@link UserDto}, or {@code null} if no user is found
     */
    @Override
    public UserDto findUserByEmail(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail);
        UserDto userDto = UserConverter.convertToUserDto(user);
        return userDto;
    }

    /**
     * Checks whether a user exists with the given email.
     *
     * @param userEmail the email to check
     * @return {@code true} if a user exists with the given email, {@code false} otherwise
     */
    @Override
    public boolean userExistsByEmail(String userEmail) {
        return userRepository.userExistsByEmail(userEmail);
    }

    /**
     * Retrieves a paginated list of users after a given ID.
     * <p>
     * This method is useful for implementing cursor-based pagination.
     * </p>
     *
     * @param lastId the ID after which users should be retrieved
     * @param size the maximum number of users to retrieve
     * @return a sorted list of {@link UserDto} objects
     */
    @Override
    public List<UserDto> findUsersAfterId(Long lastId, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("userId").ascending());
        List<User> users = userRepository.findNextPage(lastId, pageable);
        return users.stream()
                .map(UserConverter::convertToUserDto)
                .sorted(Comparator.comparing(UserDto::getUserId))
                .collect(Collectors.toList());
    }

}
