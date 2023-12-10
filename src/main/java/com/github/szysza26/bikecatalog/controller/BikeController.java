package com.github.szysza26.bikecatalog.controller;

import com.github.szysza26.bikecatalog.model.Bike;
import com.github.szysza26.bikecatalog.service.BikeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BikeController {

    private BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Bike> bikes = bikeService.getAllBikes();
        model.addAttribute("bikes", bikes);
        return "index";
    }

}
