package com.github.szysza26.bikecatalog.repository;

import com.github.szysza26.bikecatalog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> getCategoriesByParent(Category parent);
}
