package com.glowmart.shop_management.service.implementation;

import com.glowmart.shop_management.converter.CategoryConverter;
import com.glowmart.shop_management.dto.CategoryDto;
import com.glowmart.shop_management.entity.Category;
import com.glowmart.shop_management.exception.DuplicateCategoryException;
import com.glowmart.shop_management.exception.NotFoundException;
import com.glowmart.shop_management.repository.CategoryRepository;
import com.glowmart.shop_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        categoryDto.setCategoryName(categoryDto.getCategoryName().toLowerCase());
        if(categoryRepository.existsCategoryByName(categoryDto.getCategoryName())){
            throw new DuplicateCategoryException(categoryDto.getCategoryName() + " is already exists!");
        }
        categoryDto.setCreatedAt(LocalDateTime.now());
        Category category = CategoryConverter.convertToCategory(categoryDto);
        return CategoryConverter.convertToCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategoryById(Long id, CategoryDto categoryDto) {
        Optional<Category> categoryById = categoryRepository.findById(id);

        if(categoryById.isEmpty()){
            throw new NotFoundException("There is no category by id:" + id + "!");
        }

        Category updateCategoryById = categoryById.get();

        if (categoryDto.getCategoryName().equalsIgnoreCase(updateCategoryById.getCategoryName())){
            throw new DuplicateCategoryException(categoryDto.getCategoryName().toLowerCase() + " is same with the old category name!");
        }

        if(categoryRepository.existsCategoryByName(categoryDto.getCategoryName().toLowerCase())){
            throw new DuplicateCategoryException(categoryDto.getCategoryName().toLowerCase() + " is already exists!");
        }

        updateCategoryById.setCategoryName(categoryDto.getCategoryName().toLowerCase());
        updateCategoryById.setUpdatedAt(LocalDateTime.now());
        return CategoryConverter.convertToCategoryDto(categoryRepository.save(updateCategoryById));
    }

    @Override
    public CategoryDto deleteCategoryById(Long id) {
        Optional<Category> categoryById = categoryRepository.findById(id);
        if(categoryById.isEmpty()){
            throw new NotFoundException("There is no category by id:" + id + "!");
        } else {
            try {
                categoryRepository.deleteById(id);
            } catch(Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return CategoryConverter.convertToCategoryDto(categoryById.get());
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> allCategory = categoryRepository.findAll();
        return allCategory.stream().map(CategoryConverter::convertToCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryConverter::convertToCategoryDto)
                .orElseThrow(() -> new NotFoundException("There is no category by id:" + id + "!"));
    }

    @Override
    public CategoryDto getCategoryByName(String name) {
        String categoryName = name.toLowerCase();
        return categoryRepository.getCategoryByName(categoryName)
                .map(CategoryConverter::convertToCategoryDto)
                .orElseThrow(() -> new NotFoundException("There is no category by name:" + categoryName + "!"));
    }

}
