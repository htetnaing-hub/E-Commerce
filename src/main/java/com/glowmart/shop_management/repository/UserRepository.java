package com.glowmart.shop_management.repository;

import com.glowmart.shop_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userEmail = :userEmail")
    User findUserByEmail(@Param("userEmail") String userEmail);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.userEmail = :userEmail")
    boolean userExistsByEmail(@Param("userEmail") String userEmail);
}
