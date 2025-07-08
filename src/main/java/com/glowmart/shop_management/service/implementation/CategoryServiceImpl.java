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
        if(categoryRepository.existsCategoryByName(categoryDto.getCategoryName())){
            throw new DuplicateCategoryException(categoryDto.getCategoryName() + " is already exists!");
        }
        categoryDto.setCreatedAt(LocalDateTime.now());
        Category category = CategoryConverter.convertToCategory(categoryDto);
        return CategoryConverter.convertToCategoryDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategoryById(Long id, CategoryDto categoryDto) {
        Optional<Category> findCategoryById = categoryRepository.findById(id);
        if(findCategoryById.isEmpty()){
            throw new NotFoundException("There is no category by id:" + id + "!");
        }
        Category updateCategoryById = findCategoryById.get();
        updateCategoryById.setCategoryName(categoryDto.getCategoryName());
        updateCategoryById.setUpdatedAt(LocalDateTime.now());
        return CategoryConverter.convertToCategoryDto(categoryRepository.save(updateCategoryById));
    }

    @Override
    public CategoryDto deleteCategoryById(Long id) {
        Optional<Category> findCategoryById = categoryRepository.findById(id);
        if(findCategoryById.isEmpty()){
            throw new NotFoundException("There is no category by id:" + id + "!");
        }
        try {
            categoryRepository.deleteById(id);
        } catch(Exception exception) {
            //return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            System.out.println(exception.getMessage() + "==>" + HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return CategoryConverter.convertToCategoryDto(findCategoryById.get());
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> allCategory = categoryRepository.findAll();
        return allCategory.stream().map(CategoryConverter::convertToCategoryDto).collect(Collectors.toList());
    }

}
