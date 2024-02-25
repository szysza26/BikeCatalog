package com.github.szysza26.bikecatalog.repository;

import com.github.szysza26.bikecatalog.model.Bike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BikeRepository extends JpaRepository<Bike, Long> {
    Page<Bike> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Bike> findByNameContainingIgnoreCaseAndBrandId(String name, Long brand, Pageable pageable);
    Page<Bike> findByNameContainingIgnoreCaseAndCategoryIdIn(String name, Set<Long> category, Pageable pageable);
    Page<Bike> findByNameContainingIgnoreCaseAndBrandIdAndCategoryIdIn(String name, Long brand, Set<Long> category, Pageable pageable);
}
