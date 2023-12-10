package com.github.szysza26.bikecatalog.repository;

import com.github.szysza26.bikecatalog.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeRepository extends JpaRepository<Bike, Long> {
}
