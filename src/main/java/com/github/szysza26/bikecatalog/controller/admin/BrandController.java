package com.github.szysza26.bikecatalog.controller.admin;

import com.github.szysza26.bikecatalog.model.Brand;
import com.github.szysza26.bikecatalog.service.BrandService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BrandController {

    private BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/admin/brands")
    public String index(Model model) {
        List<Brand> brands =  brandService.getAllBrands();
        model.addAttribute("brands", brands);
        return "admin/brand/index";
    }

    @GetMapping("/admin/brands/new")
    public String newBrand(Model model) {
        model.addAttribute("brand", new Brand());
        return "admin/brand/form";
    }

    @GetMapping("/admin/brands/{id}")
    public String editBrand(Model model, @PathVariable long id) {
        Brand brand = brandService.getBrand(id);
        model.addAttribute("brand", brand);
        return "admin/brand/form";
    }

    @PostMapping("/admin/brands/save")
    public String saveBrand(@ModelAttribute Brand brand) {
        brandService.saveBrand(brand);
        return "redirect:/admin/brands";
    }

    @PostMapping("/admin/brands/delete")
    public String deleteBrand(@RequestParam long id) {
        Brand brand = brandService.getBrand(id);
        brandService.deleteBrand(brand);
        return "redirect:/admin/brands";
    }
}
