package com.github.szysza26.bikecatalog.service;

import com.github.szysza26.bikecatalog.InitialDataLoader;
import com.github.szysza26.bikecatalog.model.Category;
import com.github.szysza26.bikecatalog.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class CategoryServiceTest {

    @MockBean
    InitialDataLoader initialDataLoader;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;

    private Category c1;
    private Category c1A;
    private Category c1AI;
    private Category c1AII;
    private Category c1B;

    @BeforeEach
    void setUp() {
        c1 = new Category("Category 1");
        c1A = new Category("Category 1 A");
        c1.addChild(c1A);
        c1AI = new Category("Category 1 A I");
        c1A.addChild(c1AI);
        c1AII = new Category("Category 1 A II");
        c1A.addChild(c1AII);
        c1B = new Category("Category 1 B");
        c1.addChild(c1B);
        categoryRepository.save(c1);
    }

    @Test
    void getFullNameTest() {
        String fullName = categoryService.getFullName(c1AI);
        assertEquals("Category 1 -> Category 1 A -> Category 1 A I", fullName);
    }

    @Test
    void getCategoriesHierarchicalTest() {
        LinkedHashMap<Category, String> categories = categoryService.getCategoriesHierarchical(null, "", null);
        assertEquals(5, categories.size());
        assertEquals("Category 1 -> Category 1 A -> Category 1 A I", categories.values().stream().toList().get(2));
    }

    @Test
    void getAllSubCategoriesTest() {
        List<Category> subCategoriesC1 = categoryService.getAllSubCategories(c1);
        assertEquals(4, subCategoriesC1.size());
        assertTrue(subCategoriesC1.containsAll(List.of(c1A, c1AI, c1AII, c1B)));

        List<Category> subCategoriesC1A = categoryService.getAllSubCategories(c1A);
        assertEquals(2, subCategoriesC1A.size());
        assertTrue(subCategoriesC1A.containsAll(List.of(c1AI, c1AII)));
    }
}
