package com.glowmart.shop_management.repository;

import com.glowmart.shop_management.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userEmail = :userEmail")
    User findUserByEmail(@Param("userEmail") String userEmail);

    @Query("SELECT u FROM User u WHERE u.userEmail = :userEmail")
    Optional<User> findByEmail (String userEmail);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.userEmail = :userEmail")
    boolean userExistsByEmail(@Param("userEmail") String userEmail);

    @Query("SELECT u FROM User u WHERE u.userId > :lastId ORDER BY u.userId ASC")
    List<User> findNextPage(@Param("lastId") Long lastId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.lastLoginTime = CURRENT_TIMESTAMP WHERE u.userEmail = :email")
    void updateLoginTime(@Param("email") String email);

}
