package com.github.szysza26.bikecatalog.service;

import com.github.szysza26.bikecatalog.model.Bike;
import com.github.szysza26.bikecatalog.repository.BikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class BikeServiceTest {

    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private BikeService bikeService;

    @BeforeEach
    void setUp() {
        List<Bike> bikes = Arrays.asList(
            new Bike("Test Bike 1 Name", "Test Bike 1 Description"),
            new Bike("Test Bike 2 Name", "Test Bike 2 Description"),
            new Bike("Test Bike 3 Name", "Test Bike 3 Description")
        );
        bikeRepository.saveAll(bikes);
    }

    @Test
    void getAllBikesTest() {
        Page<Bike> bikes = bikeService.getBikes(0, 12, Sort.Direction.ASC, "name");
        assertEquals(3, bikes.getContent().size());
    }

}
