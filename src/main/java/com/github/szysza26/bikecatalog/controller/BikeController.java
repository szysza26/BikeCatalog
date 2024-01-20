package com.github.szysza26.bikecatalog.controller;

import com.github.szysza26.bikecatalog.dto.SearchBikeRequest;
import com.github.szysza26.bikecatalog.model.Bike;
import com.github.szysza26.bikecatalog.model.Brand;
import com.github.szysza26.bikecatalog.model.Category;
import com.github.szysza26.bikecatalog.service.BikeService;
import com.github.szysza26.bikecatalog.service.BrandService;
import com.github.szysza26.bikecatalog.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BikeController {

    private BikeService bikeService;
    private CategoryService categoryService;
    private BrandService brandService;

    public BikeController(BikeService bikeService, BrandService brandService, CategoryService categoryService) {
        this.bikeService = bikeService;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = {"/", "/bikes"})
    public String index(SearchBikeRequest search, Model model) {
        Page<Bike> bikes = bikeService.getBikes(search);
        model.addAttribute("bikes", bikes.getContent());

        model.addAttribute("pageNumber", bikes.getNumber());
        model.addAttribute("totalPages", bikes.getTotalPages());

        model.addAttribute("search", search);

        List<Brand> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        return "bike/index";
    }

    @GetMapping("/bikes/{id}")
    public String show(@PathVariable long id, Model model) {
        Bike bike = bikeService.getBike(id);
        model.addAttribute("bike", bike);

        String brand = bike.getBrand() != null ? bike.getBrand().getName() : null;
        model.addAttribute("brand", brand);

        String category = categoryService.getFullName(bike.getCategory());
        model.addAttribute("category", category);

        return "bike/show";
    }

}
