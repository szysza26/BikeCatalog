package com.github.szysza26.bikecatalog.service;

import com.github.szysza26.bikecatalog.model.Bike;
import com.github.szysza26.bikecatalog.repository.BikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {

    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public List<Bike> getAllBikes() {
        return bikeRepository.findAll();
    }

}
