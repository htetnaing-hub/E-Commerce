package com.glowmart.shop_management.service.implementation;

import com.glowmart.shop_management.common.CommonFunction;
import com.glowmart.shop_management.converter.UserConverter;
import com.glowmart.shop_management.dto.UserDto;
import com.glowmart.shop_management.entity.Role;
import com.glowmart.shop_management.entity.User;
import com.glowmart.shop_management.exception.DuplicateException;
import com.glowmart.shop_management.exception.NotFoundException;
import com.glowmart.shop_management.exception.NotValidException;
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

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    /**
     * Updates the user information for the specified user ID.
     * <p>
     * This method performs validation on the input parameters and ensures that the new email and phone number
     * do not conflict with existing records. It throws exceptions for invalid input, non-existent users,
     * or duplicate data. If all checks pass, the user's email, name, and phone number are updated in the database.
     * </p>
     *
     * @param id    the unique identifier of the user to update; must be a valid numeric string
     * @param email the new email address; must not match the current email or any existing user's email
     * @param name  the new name; must contain only letters and spaces
     * @param phone the new phone number; must not match the current phone or any existing user's phone
     * @throws NotValidException   if the ID or name format is invalid
     * @throws NotFoundException   if no user exists with the given ID
     * @throws DuplicateException  if the email or phone number is unchanged or already used by another user
     * @throws RuntimeException    if an unexpected error occurs during database save
     */
    @Override
    public void updateUserById(String id, String email, String name, String phone) {
        if(CommonFunction.isValidId(id) == false){
            throw new NotValidException("User id is not valid! Id must be only number, not null and greater than 0.");
        }

        if (CommonFunction.isValidName(name) == false){
            throw new NotValidException("User name is not valid! Please enter letters and spaces.");
        }

        Long userId = Long.parseLong(id);
        Optional<User> userById = userRepository.findById(userId);

        if (userById.isEmpty()) {
            throw new NotFoundException("There is no user by id:" + id + "!");
        }

        if (email.equalsIgnoreCase(userById.get().getUserEmail())) {
            throw new DuplicateException(email.toLowerCase() + " is same with old your email!");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateException(email.toLowerCase() + " is already exists!");
        }

        if (phone.equalsIgnoreCase(userById.get().getUserPhone())) {
            throw new DuplicateException(phone.toLowerCase() + " is same with old your phone number!");
        }

        if (userRepository.findByPhone(phone).isPresent()) {
            throw new DuplicateException(phone + " is already exists!");
        }

        userById.get().setUserEmail(email);
        userById.get().setUserName(name);
        userById.get().setUserPhone(phone);
        userById.get().setUpdatedAt(LocalDateTime.now());

        try {
            userRepository.save(userById.get());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves a user by their unique ID and converts the result to a {@link UserDto}.
     *
     * @param id the unique identifier of the user to retrieve; must be a valid numeric string
     * @return a {@link UserDto} representing the user with the given ID
     */
    @Override
    public UserDto findUserById(String id) {
        Long userId = Long.parseLong(id);
        User user = userRepository.findById(userId).get();
        return UserConverter.convertToUserDto(user);
    }

}
