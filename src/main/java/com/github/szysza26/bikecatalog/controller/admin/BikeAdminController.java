package com.github.szysza26.bikecatalog.controller.admin;

import com.github.szysza26.bikecatalog.model.Bike;
import com.github.szysza26.bikecatalog.service.BikeService;
import com.github.szysza26.bikecatalog.service.BrandService;
import com.github.szysza26.bikecatalog.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class BikeAdminController {

    private BikeService bikeService;
    private BrandService brandService;
    private CategoryService categoryService;

    public BikeAdminController(BikeService bikeService, BrandService brandService, CategoryService categoryService) {
        this.bikeService = bikeService;
        this.brandService = brandService;
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/bikes")
    public String index(Model model) {
        List<Bike> bikes = bikeService.getAllBikes();
        model.addAttribute("bikes", bikes);
        return "admin/bike/index";
    }

    @GetMapping("/admin/bikes/new")
    public String newBike(Model model) {
        model.addAttribute("bike", new Bike());
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/bike/form";
    }

    @GetMapping("/admin/bikes/{id}")
    public String editBike(Model model, @PathVariable long id) {
        Bike bike = bikeService.getBike(id);
        model.addAttribute("bike", bike);
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/bike/form";
    }

    @PostMapping("/admin/bikes/save")
    public String saveBike(@ModelAttribute Bike bike, @RequestParam MultipartFile thumbnailFile) {
        bikeService.saveBike(bike, thumbnailFile);
        return "redirect:/admin/bikes";
    }

    @PostMapping("/admin/bikes/delete")
    public String deleteBike(@RequestParam long id) {
        Bike bike = bikeService.getBike(id);
        bikeService.deleteBike(bike);
        return "redirect:/admin/bikes";
    }
}
