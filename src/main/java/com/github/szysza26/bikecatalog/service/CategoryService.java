package com.github.szysza26.bikecatalog.service;

import com.github.szysza26.bikecatalog.controller.NotFoundException;
import com.github.szysza26.bikecatalog.model.Category;
import com.github.szysza26.bikecatalog.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(long id) {
        return categoryRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }

    public List<Category> getCategoryPath (Category category) {
        List<Category> path = new ArrayList<>();

        while (category != null) {
            path.add(category);
            category = category.getParent();
        }

        Collections.reverse(path);

        return path;
    }

    public String getFullName (Category category) {
        if(category == null)
            return null;

        List<Category> categoryPath = getCategoryPath(category);
        return categoryPath.stream().map(Category::getName).collect(Collectors.joining(" -> "));
    }

    public List<Category> getSortedCategories(Category parent) {
        return getSortedCategories(parent, null);
    }

    public List<Category> getSortedCategories(Category parent, Category stopAt) {
        List<Category> sortedCategories = new ArrayList<>();

        if(stopAt != null && stopAt.equals(parent))
            return sortedCategories;

        if(parent != null)
            sortedCategories.add(parent);

        List<Category> children = categoryRepository.getCategoriesByParent(parent);
        for(Category category : children) {
            sortedCategories.addAll(getSortedCategories(category, stopAt));
        }

        return sortedCategories;
    }
}
