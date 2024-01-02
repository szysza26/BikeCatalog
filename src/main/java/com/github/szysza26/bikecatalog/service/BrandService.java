package com.github.szysza26.bikecatalog.service;

import com.github.szysza26.bikecatalog.controller.NotFoundException;
import com.github.szysza26.bikecatalog.model.Brand;
import com.github.szysza26.bikecatalog.repository.BrandRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class BrandService {

    private BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        brands.sort(Comparator.comparing(Brand::getName));
        return brands;
    }

    public Brand getBrand(long id) {
        return brandRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public void deleteBrand(Brand brand) {
        brandRepository.delete(brand);
    }

}
