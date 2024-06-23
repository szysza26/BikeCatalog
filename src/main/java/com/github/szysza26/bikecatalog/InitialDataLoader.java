package com.github.szysza26.bikecatalog;

import com.github.javafaker.Faker;
import com.github.szysza26.bikecatalog.model.Bike;
import com.github.szysza26.bikecatalog.model.Brand;
import com.github.szysza26.bikecatalog.model.Category;
import com.github.szysza26.bikecatalog.model.Property;
import com.github.szysza26.bikecatalog.repository.BikeRepository;
import com.github.szysza26.bikecatalog.repository.BrandRepository;
import com.github.szysza26.bikecatalog.repository.CategoryRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Component
public class InitialDataLoader implements ApplicationRunner {

    private BikeRepository bikeRepository;
    private BrandRepository brandRepository;
    private CategoryRepository categoryRepository;
    private Faker faker;
    private Random random;

    public InitialDataLoader (BikeRepository bikeRepository, BrandRepository brandRepository,
			      CategoryRepository categoryRepository) {
	this.bikeRepository = bikeRepository;
	this.brandRepository = brandRepository;
	this.categoryRepository = categoryRepository;
	this.faker = new Faker ();
	this.random = new Random ();
    }

    @Override
    public void run (ApplicationArguments args) throws Exception {
	Brand brand1 = new Brand (faker.company ().name ());
	Brand brand2 = new Brand (faker.company ().name ());
	Brand brand3 = new Brand (faker.company ().name ());

	Category category1 = new Category (faker.commerce ().productName ());
	Category category1a = new Category (faker.commerce ().productName (), category1);
	Category category1b = new Category (faker.commerce ().productName (), category1);
	Category category2 = new Category (faker.commerce ().productName ());

	Set<Bike> bikes = new HashSet<> ();
	for (int i = 0; i < 30; i++) {
	    int randomInt = random.nextInt (4);

	    Brand brand;
	    switch (randomInt) {
		case 0:
		    brand = brand1;
		    break;
		case 1:
		    brand = brand2;
		    break;
		case 2:
		    brand = brand3;
		    break;
		default:
		    brand = null;
		    break;
	    }

	    randomInt = random.nextInt (5);
	    Category category;
	    switch (randomInt) {
		case 0:
		    category = category1;
		    break;
		case 1:
		    category = category1a;
		    break;
		case 2:
		    category = category1b;
		    break;
		case 3:
		    category = category2;
		    break;
		default:
		    category = null;
		    break;
	    }

	    String name = faker.commerce ().productName ();
	    String content = faker.lorem ().paragraph (3);

	    String thumbnails;
	    int thumbnailsNumber = (i % 30) + 1;
	    if (thumbnailsNumber <= 12) {
		thumbnails = "bike_" + thumbnailsNumber + ".jpg";
	    } else {
		thumbnails = null;
	    }

	    Bike bike = new Bike (name, content, brand, category, thumbnails);
	    bikes.add (bike);

	    randomInt = random.nextInt (8);
	    for (int j = 0; j < randomInt; j++) {
		Property property = new Property (faker.commerce ().productName (), faker.commerce ().productName ());
		property.setBike (bike);
		bike.getProperties ().add (property);
	    }
	}

	brandRepository.save (brand1);
	brandRepository.save (brand2);
	brandRepository.save (brand3);

	categoryRepository.save (category1);
	categoryRepository.save (category1a);
	categoryRepository.save (category1b);
	categoryRepository.save (category2);

	bikeRepository.saveAll (bikes);
    }
}
