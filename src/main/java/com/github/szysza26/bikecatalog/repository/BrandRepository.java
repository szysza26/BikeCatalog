package com.github.szysza26.bikecatalog.repository;

import com.github.szysza26.bikecatalog.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
