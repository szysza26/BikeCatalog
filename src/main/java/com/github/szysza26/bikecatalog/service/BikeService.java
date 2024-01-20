package com.github.szysza26.bikecatalog.service;

import com.github.szysza26.bikecatalog.controller.NotFoundException;
import com.github.szysza26.bikecatalog.dto.SearchBikeRequest;
import com.github.szysza26.bikecatalog.model.Bike;
import com.github.szysza26.bikecatalog.model.Property;
import com.github.szysza26.bikecatalog.repository.BikeRepository;
import com.github.szysza26.bikecatalog.service.utils.FileUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

@Service
public class BikeService {

    public final static String BIKE_THUMBNAIL_UPLOAD_DIR = "public/thumbnails/";
    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    public List<Bike> getAllBikes() {
        List<Bike> bikes = bikeRepository.findAll();
        bikes.sort(Comparator.comparing(Bike::getName));
        return bikes;
    }

    public Page<Bike> getBikes(SearchBikeRequest search) {
        String[] splitSort = search.getSort().split("\\.", 2);
        String sortBy = splitSort[0];
        Sort.Direction sortDirection = splitSort[1].equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(search.getPageNumber(), search.getPageSize(), sortDirection, sortBy);

        if (search.getBrand() == null && search.getCategory() == null) {
            return bikeRepository.findByNameContainingIgnoreCase(search.getName(), pageable);
        } else if (search.getBrand() != null && search.getCategory() == null) {
            return bikeRepository.findByNameContainingIgnoreCaseAndBrandId(search.getName(), search.getBrand(), pageable);
        } else if (search.getBrand() == null && search.getCategory() != null) {
            return bikeRepository.findByNameContainingIgnoreCaseAndCategoryId(search.getName(), search.getCategory(), pageable);
        } else {
            return bikeRepository.findByNameContainingIgnoreCaseAndBrandIdAndCategoryId(search.getName(), search.getBrand(), search.getCategory(), pageable);
        }
    }

    public Bike getBike(long id) {
        return bikeRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public Bike saveBike(Bike bike, MultipartFile thumbnailFile) {
        for(Property property : bike.getProperties()) {
            property.setBike(bike);
        }
        uploadThumbnail(bike, thumbnailFile);
        return bikeRepository.save(bike);
    }

    public void deleteBike(Bike bike) {
        removeThumbnail(bike);
        bikeRepository.delete(bike);
    }

    private void uploadThumbnail(Bike bike, MultipartFile thumbnailFile) {
        if(thumbnailFile == null || thumbnailFile.isEmpty()) {
            return;
        }

        removeThumbnail(bike);

        String name = FileUtil.generateUniqueFileName(thumbnailFile.getOriginalFilename());
        Path path = Path.of(BIKE_THUMBNAIL_UPLOAD_DIR + name);

        if(FileUtil.saveFile(path, thumbnailFile)) {
            bike.setThumbnail(name);
        }
    }

    private void removeThumbnail(Bike bike) {
        if(bike.getThumbnail() == null) {
            return;
        }

        Path thumbnailPath = Path.of(BIKE_THUMBNAIL_UPLOAD_DIR + bike.getThumbnail());
        if(FileUtil.deleteFile(thumbnailPath)) {
            bike.setThumbnail(null);
        }
    }
}
