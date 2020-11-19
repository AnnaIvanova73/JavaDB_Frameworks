package softuni.jsonprocessing.service;

import softuni.jsonprocessing.domain.entities.Car;
import softuni.jsonprocessing.domain.entities.Customer;

public interface RandomValuesService {

    Customer getRandomCustomer() throws Exception;

    Car getRandomCar() throws Exception;

    int getDiscount();

    int getUnderAgeDiscount();
}
