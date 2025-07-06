package com.glowmart.shop_management.service.implementation;

import com.glowmart.shop_management.converter.CategoryConverter;
import com.glowmart.shop_management.dto.CategoryDto;
import com.glowmart.shop_management.entity.Category;
import com.glowmart.shop_management.exception.DuplicateCategoryException;
import com.glowmart.shop_management.repository.CategoryRepository;
import com.glowmart.shop_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if(categoryRepository.existsCategoryByName(categoryDto.getCategoryName())){
            throw new DuplicateCategoryException(categoryDto.getCategoryName() + " is already exists!");
        }
        categoryDto.setCreatedAt(LocalDateTime.now());
        Category category = CategoryConverter.convertToCategory(categoryDto);
        return CategoryConverter.convertToCategoryDto(categoryRepository.save(category));
    }

}
