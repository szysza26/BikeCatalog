package com.github.szysza26.bikecatalog.service;

import com.github.szysza26.bikecatalog.controller.NotFoundException;
import com.github.szysza26.bikecatalog.dto.SearchBikeRequest;
import com.github.szysza26.bikecatalog.model.Bike;
import com.github.szysza26.bikecatalog.repository.BikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BikeServiceTest {

    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private BikeService bikeService;

    @BeforeEach
    void setUp() {
        List<Bike> bikes = new ArrayList<>();
        for(int i = 0; i < 18; i++) {
            bikes.add(new Bike(String.format("Test Bike %d Name", i + 1),
                    String.format("Test Bike %d Description", i + 1), null, null, null));
        }
        bikeRepository.saveAll(bikes);
    }

    @Test
    void getBikesTest() {
        Page<Bike> bikes = bikeService.getBikes(new SearchBikeRequest(0, 12, "name.ASC", "", null, null));
        assertEquals(12, bikes.getContent().size());
    }

    @Test
    void getBikesByNameTest() {
        Page<Bike> bikes = bikeService.getBikes(new SearchBikeRequest(0, 12, "name.ASC", "bike 10", null, null));
        assertEquals(1, bikes.getContent().size());
        assertEquals("Test Bike 10 Name", bikes.getContent().get(0).getName());
    }

    @Test
    void getBikeTest() {
        Bike bike = bikeService.getBike(10);
        assertEquals(10, bike.getId());
    }

    @Test
    void getBikeNotFoundTest() {
        assertThrows(NotFoundException.class, () -> bikeService.getBike(20));
    }

}
