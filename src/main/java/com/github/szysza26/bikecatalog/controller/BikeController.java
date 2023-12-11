package com.github.szysza26.bikecatalog.controller;

import com.github.szysza26.bikecatalog.model.Bike;
import com.github.szysza26.bikecatalog.service.BikeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BikeController {

    private BikeService bikeService;
    private final int PAGE_SIZE = 12;
    private final Sort.Direction SORT_DIRECTION = Sort.Direction.ASC;
    private final String SORT_BY = "id";

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0") int pageNumber, Model model) {
        Page<Bike> bikes = bikeService.getBikes(pageNumber, PAGE_SIZE, SORT_DIRECTION, SORT_BY);
        model.addAttribute("bikes", bikes.getContent());
        model.addAttribute("pageNumber", bikes.getNumber());
        model.addAttribute("totalPages", bikes.getTotalPages());
        return "index";
    }

}
