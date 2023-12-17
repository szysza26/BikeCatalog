package com.github.szysza26.bikecatalog.controller.admin;

import com.github.szysza26.bikecatalog.model.Category;
import com.github.szysza26.bikecatalog.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/categories")
    public String index(Model model) {
        List<Category> categories =  categoryService.getSortedCategories(null);
        model.addAttribute("categories", categories);
        return "admin/category/index";
    }

    @GetMapping("/admin/categories/new")
    public String newCategory(Model model) {
        model.addAttribute("category", new Category());
        List<Category> categories = categoryService.getSortedCategories(null);
        model.addAttribute("parents", categories);
        return "admin/category/form";
    }

    @GetMapping("/admin/categories/{id}")
    public String editCategories(Model model, @PathVariable long id) {
        Category category = categoryService.getCategory(id);
        model.addAttribute("category", category);
        List<Category> categories = categoryService.getSortedCategories(null, category);
        model.addAttribute("parents", categories.stream().filter(c -> c.getId() != id).toList());
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
