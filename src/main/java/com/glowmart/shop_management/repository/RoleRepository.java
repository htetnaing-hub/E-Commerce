package com.glowmart.shop_management.repository;

import com.glowmart.shop_management.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE UPPER(r.roleName) = UPPER(:roleName)")
    Role findRoleByInputRole(@Param("roleName") String roleName);

    @Query("SELECT COUNT(r) FROM Role r WHERE UPPER(r.roleName) = UPPER(:roleName)")
    int countByInputRole(@Param("roleName") String roleName);

}
