package com.glowmart.shop_management.service;

import com.glowmart.shop_management.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategoryById(String id, CategoryDto categoryDto);

    CategoryDto deleteCategoryById(String id);

    List<CategoryDto> getAllCategory();

    CategoryDto getCategoryById(Long id);

    CategoryDto getCategoryByName(String name);
}
