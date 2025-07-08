package com.glowmart.shop_management.service;

import com.glowmart.shop_management.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategoryById(Long id, CategoryDto categoryDto);

    CategoryDto deleteCategoryById(Long id);

    List<CategoryDto> getAllCategory();
}
