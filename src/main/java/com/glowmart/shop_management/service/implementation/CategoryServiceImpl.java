package com.glowmart.shop_management.service.implementation;

import com.glowmart.shop_management.common.CommonFunction;
import com.glowmart.shop_management.converter.CategoryConverter;
import com.glowmart.shop_management.dto.CategoryDto;
import com.glowmart.shop_management.entity.Category;
import com.glowmart.shop_management.exception.DuplicateException;
import com.glowmart.shop_management.exception.NotFoundException;
import com.glowmart.shop_management.exception.NotValidException;
import com.glowmart.shop_management.repository.CategoryRepository;
import com.glowmart.shop_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementation of {@link CategoryService} that provides business logic
 * for managing {@link Category} entities.
 * <p>
 * This service handles creation, updating, deletion, and retrieval of categories.
 * It performs validation on input data, ensures uniqueness of category names,
 * and throws appropriate custom exceptions such as {@link NotValidException},
 * {@link DuplicateException}, and {@link NotFoundException}.
 * </p>
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    private final ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();

    /**
     * Creates a new category after validating its name and ensuring uniqueness.
     *
     * @param categoryDto the DTO containing category details
     * @return the created {@link CategoryDto}
     * @throws NotValidException if the category name is invalid
     * @throws DuplicateException if a category with the same name already exists
     */
    @Override
    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto) {
        if (CommonFunction.isValidName(categoryDto.getCategoryName()) == false){
            throw new NotValidException("Category name is not valid! Please enter letters and spaces.");
        }
        categoryDto.setCategoryName(categoryDto.getCategoryName().toLowerCase());

        Object lock = locks.computeIfAbsent(categoryDto.getCategoryName(), k -> new Object());

        synchronized (lock) {
            if(categoryRepository.existsCategoryByName(categoryDto.getCategoryName())){
                throw new DuplicateException(categoryDto.getCategoryName() + " is already exists!");
            }
            categoryDto.setCreatedAt(LocalDateTime.now());
            Category category = CategoryConverter.convertToCategory(categoryDto);
            return CategoryConverter.convertToCategoryDto(categoryRepository.save(category));
        }
    }

    /**
     * Updates an existing category by its ID.
     *
     * @param id the ID of the category to update
     * @param categoryDto the DTO containing updated category details
     * @return the updated {@link CategoryDto}
     * @throws NotValidException if the ID or category name is invalid
     * @throws NotFoundException if no category exists with the given ID
     * @throws DuplicateException if the new category name already exists or matches the old one
     */
    @Override
    public CategoryDto updateCategoryById(String id, CategoryDto categoryDto) {
        if(CommonFunction.isValidId(id) == false){
            throw new NotValidException("Category id is not valid! Id must be only number, not null and greater than 0.");
        }

        if (CommonFunction.isValidName(categoryDto.getCategoryName()) == false){
            throw new NotValidException("Category name is not valid! Please enter letters and spaces.");
        }

        Long categoryId = Long.parseLong(id);
        Optional<Category> categoryById = categoryRepository.findById(categoryId);

        if(categoryById.isEmpty()){
            throw new NotFoundException("There is no category by id:" + id + "!");
        }

        Category updateCategoryById = categoryById.get();

        if (categoryDto.getCategoryName().equalsIgnoreCase(updateCategoryById.getCategoryName())){
            throw new DuplicateException(categoryDto.getCategoryName().toLowerCase() + " is same with the old category name!");
        }

        if(categoryRepository.existsCategoryByName(categoryDto.getCategoryName().toLowerCase())){
            throw new DuplicateException(categoryDto.getCategoryName().toLowerCase() + " is already exists!");
        }

        updateCategoryById.setCategoryName(categoryDto.getCategoryName().toLowerCase());
        updateCategoryById.setUpdatedAt(LocalDateTime.now());
        try {
            return CategoryConverter.convertToCategoryDto(categoryRepository.save(updateCategoryById));
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id the ID of the category to delete
     * @return the deleted {@link CategoryDto}
     * @throws NotValidException if the ID is invalid
     * @throws NotFoundException if no category exists with the given ID
     */
    @Override
    public CategoryDto deleteCategoryById(String id) {
        if(CommonFunction.isValidId(id) == false){
            throw new NotValidException("Category id is not valid! Id must be only number, not null and greater than 0.");
        }
        Long categoryId = Long.parseLong(id);
        Optional<Category> categoryById = categoryRepository.findById(categoryId);
        if(categoryById.isEmpty()){
            throw new NotFoundException("There is no category by id:" + id + "!");
        } else {
            try {
                categoryRepository.deleteById(categoryId);
            } catch(Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return CategoryConverter.convertToCategoryDto(categoryById.get());
    }

    /**
     * Retrieves all categories from the repository.
     *
     * @return a list of {@link CategoryDto} representing all categories
     */
    @Override
    public List<CategoryDto> getAllCategory() {
        try {
            List<Category> allCategory = categoryRepository.findAll();
            return allCategory.stream().map(CategoryConverter::convertToCategoryDto).collect(Collectors.toList());
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id the ID of the category to retrieve
     * @return the found {@link CategoryDto}
     * @throws NotValidException if the ID is invalid
     * @throws NotFoundException if no category exists with the given ID
     */
    @Override
    public CategoryDto getCategoryById(String id) {
        if (CommonFunction.isValidId(id) == false){
            throw new NotValidException("Category id is not valid! Id must be only number, not null and greater than 0.");
        }
        Long categoryId = Long.parseLong(id);
        return categoryRepository.findById(categoryId)
                .map(CategoryConverter::convertToCategoryDto)
                .orElseThrow(() -> new NotFoundException("There is no category by id:" + id + "!"));
    }

    /**
     * Retrieves a category by its name.
     *
     * @param name the name of the category to retrieve
     * @return the found {@link CategoryDto}
     * @throws NotValidException if the name is invalid
     * @throws NotFoundException if no category exists with the given name
     */
    @Override
    public CategoryDto getCategoryByName(String name) {
        if (CommonFunction.isValidName(name) == false){
            throw new NotValidException("Category name is not valid! Please enter letters and spaces.");
        }
        String categoryName = name.toLowerCase();
        return categoryRepository.getCategoryByName(categoryName)
                .map(CategoryConverter::convertToCategoryDto)
                .orElseThrow(() -> new NotFoundException("There is no category by name:" + categoryName + "!"));
    }

}
