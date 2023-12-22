package com.github.szysza26.bikecatalog.service;

import com.github.szysza26.bikecatalog.controller.NotFoundException;
import com.github.szysza26.bikecatalog.model.Category;
import com.github.szysza26.bikecatalog.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public LinkedHashMap<Category, String> getCategoriesHierarchical(Category parent, String parentPath, Category stopAt) {
        LinkedHashMap<Category, String> categories = new LinkedHashMap<>();

        List<Category> nodes = parent != null ? parent.getChildren() : categoryRepository.findByParentIsNull();
        nodes.sort(Comparator.comparing(Category::getName));
        for(Category node : nodes) {
            if(node.equals(stopAt))
                continue;
            String path = parentPath + node.getName();
            categories.put(node, path);
            categories.putAll(getCategoriesHierarchical(node, path + " -> ", stopAt));
        }

        return categories;
    }
}
