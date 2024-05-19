package com.example.workoutWonderland.service.impl;

import com.example.workoutWonderland.entity.Category;
import com.example.workoutWonderland.exception.ResourceNotFoundException;
import com.example.workoutWonderland.repository.ICategoryRepository;
import com.example.workoutWonderland.service.ICategoriyService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryService implements ICategoriyService {

    private final ICategoryRepository categoryRepository;
    private static final String NOT_FOUND = "Category not found";

    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) throws ResourceNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);
        return category.get();
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Category> categorySearch = categoryRepository.findById(id);
        if (categorySearch.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);
        categoryRepository.deleteById(id);
    }

    @Override
    public Set<Category> findAll() throws ResourceNotFoundException {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) throw new ResourceNotFoundException("There are no categories stored");
        return new HashSet<>(categories);
    }

    @Override
    public Category modify(Category category) throws ResourceNotFoundException {
        getCategory(category.getId());
        return categoryRepository.save(category);
    }

    private Category getCategory(Long id) throws ResourceNotFoundException {
        Optional<Category> categorySearch = categoryRepository.findById(id);
        if (categorySearch.isEmpty()) throw new ResourceNotFoundException(NOT_FOUND);
        return categorySearch.get();
    }

    public List<Category> findByIds(List<Long> categoryIds) {
        return categoryRepository.findAllById(categoryIds);
    }
}
