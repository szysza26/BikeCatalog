package com.github.szysza26.bikecatalog.service;

import com.github.szysza26.bikecatalog.controller.NotFoundException;
import com.github.szysza26.bikecatalog.model.Bike;
import com.github.szysza26.bikecatalog.repository.BikeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Bike> getBikes(int pageNumber, int pageSize, Sort.Direction sortDirection, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDirection, sortBy);
        return bikeRepository.findAll(pageable);
    }

    public Bike getBike(long id) {
        return bikeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Bike saveBike(Bike bike) {
        return bikeRepository.save(bike);
    }

    public void deleteBike(Bike bike) {
        bikeRepository.delete(bike);
    }

}
