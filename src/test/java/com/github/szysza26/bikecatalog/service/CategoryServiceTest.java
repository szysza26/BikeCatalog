package com.github.szysza26.bikecatalog.service;

import com.github.szysza26.bikecatalog.model.Category;
import com.github.szysza26.bikecatalog.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;

    private Category c1AI;

    @BeforeEach
    void setUp() {
        Category c1 = new Category("Category 1");
        Category c1A = new Category("Category 1 A");
        c1.addChild(c1A);
        c1AI = new Category("Category 1 A I");
        c1A.addChild(c1AI);

        categoryRepository.save(c1);
    }

    @Test
    void getFullNameTest() {
        String fullName = categoryService.getFullName(c1AI);
        assertEquals("Category 1 -> Category 1 A -> Category 1 A I", fullName);
    }
}
