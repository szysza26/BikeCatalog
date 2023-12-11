package com.github.szysza26.bikecatalog.service;

import com.github.szysza26.bikecatalog.model.Bike;
import com.github.szysza26.bikecatalog.repository.BikeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BikeService {

    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public Page<Bike> getBikes(int pageNumber, int pageSize, Sort.Direction sortDirection, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortDirection, sortBy);
        return bikeRepository.findAll(pageable);
    }

}
