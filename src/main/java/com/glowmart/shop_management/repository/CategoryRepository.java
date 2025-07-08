package com.glowmart.shop_management.repository;

import com.glowmart.shop_management.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Category c WHERE c.categoryName = :categoryName")
    boolean existsCategoryByName(@Param("categoryName") String categoryName);

    @Query("SELECT c FROM Category c WHERE c.categoryName = :categoryName")
    Optional<Category> getCategoryByName(String categoryName);
}
