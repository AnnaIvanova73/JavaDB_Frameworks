package softuni.jsonprocessing.service;

import softuni.jsonprocessing.domain.entities.Car;
import softuni.jsonprocessing.domain.entities.Customer;
import softuni.jsonprocessing.domain.entities.Part;
import softuni.jsonprocessing.domain.entities.Supplier;

import java.util.List;

public interface RandomValuesService {

    Customer getRandomCustomer() throws Exception;

    Car getRandomCar() throws Exception;

    int getRandomDiscount();

    int getUnderAgeDiscount();

    Supplier getRandomSupplier() throws Exception;

    List<Part> getRandomParts() throws Exception;
}
