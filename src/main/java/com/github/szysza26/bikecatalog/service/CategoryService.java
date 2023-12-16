package com.github.szysza26.bikecatalog.service;

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

}
