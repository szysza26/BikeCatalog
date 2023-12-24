package com.github.szysza26.bikecatalog.controller.admin;

import com.github.szysza26.bikecatalog.model.Category;
import com.github.szysza26.bikecatalog.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@Controller
public class CategoryAdminController {

    private CategoryService categoryService;

    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/categories")
    public String index(Model model) {
        LinkedHashMap<Category, String> categories =
                categoryService.getCategoriesHierarchical(null, "", null);
        model.addAttribute("categories", categories);
        return "admin/category/index";
    }

    @GetMapping("/admin/categories/new")
    public String newCategory(Model model) {
        model.addAttribute("category", new Category());
        LinkedHashMap<Category, String> categories =
                categoryService.getCategoriesHierarchical(null, "", null);
        model.addAttribute("categories", categories);
        return "admin/category/form";
    }

    @GetMapping("/admin/categories/{id}")
    public String editCategories(Model model, @PathVariable long id) {
        Category category = categoryService.getCategory(id);
        model.addAttribute("category", category);
        LinkedHashMap<Category, String> categories =
                categoryService.getCategoriesHierarchical(null, "", category);
        model.addAttribute("categories", categories);
        return "admin/category/form";
    }

    @PostMapping("/admin/categories/save")
    public String saveCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/admin/categories";
    }

    @PostMapping("/admin/categories/delete")
    public String deleteBrand(@RequestParam long id) {
        Category category = categoryService.getCategory(id);
        categoryService.deleteCategory(category);
        return "redirect:/admin/categories";
    }
}
