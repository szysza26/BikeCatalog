package com.github.szysza26.bikecatalog.controller;

import com.github.szysza26.bikecatalog.model.Bike;
import com.github.szysza26.bikecatalog.service.BikeService;
import com.github.szysza26.bikecatalog.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BikeController {

    private BikeService bikeService;
    private CategoryService categoryService;
    private final int PAGE_SIZE = 12;
    private final Sort.Direction SORT_DIRECTION = Sort.Direction.DESC;
    private final String SORT_BY = "createdAt";

    public BikeController(BikeService bikeService, CategoryService categoryService) {
        this.bikeService = bikeService;
        this.categoryService = categoryService;
    }

    @GetMapping(value = {"/", "/bikes"})
    public String index(@RequestParam(defaultValue = "0") int pageNumber, Model model) {
        Page<Bike> bikes = bikeService.getBikes(pageNumber, PAGE_SIZE, SORT_DIRECTION, SORT_BY);
        model.addAttribute("bikes", bikes.getContent());
        model.addAttribute("pageNumber", bikes.getNumber());
        model.addAttribute("totalPages", bikes.getTotalPages());
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
