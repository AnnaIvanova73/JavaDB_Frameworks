package softuni.jsonprocessing.service.impl.additional;

import org.springframework.stereotype.Service;
import softuni.jsonprocessing.domain.entities.Car;
import softuni.jsonprocessing.domain.entities.Customer;
import softuni.jsonprocessing.domain.repo.CarRepository;
import softuni.jsonprocessing.domain.repo.CustomerRepository;
import softuni.jsonprocessing.service.RandomValuesService;

import java.util.Optional;
import java.util.Random;

@Service
public class RandomValuesServiceImpl implements RandomValuesService {

    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;

    public RandomValuesServiceImpl(CarRepository carRepository, CustomerRepository customerRepository) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    public Customer getRandomCustomer() throws Exception {

        Random random = new Random();
        long randomCustomer = random.nextInt((int) this.customerRepository.count() - 1) + 1;
        Optional<Customer> customer = this.customerRepository.findById(randomCustomer);

        if (customer.isEmpty()) {
            throw new Exception("You have problem in RandomValuesServiceImpl.getRandomCustomer() " +
                    "couldn't retrieve value");
        }

        return customer.get();
    }

    @Override
    public Car getRandomCar() throws Exception {
        Random random = new Random();
        long randomCustomer = random.nextInt((int) this.carRepository.count() - 1) + 1;
        Optional<Car> car = this.carRepository.findById(randomCustomer);

        if (car.isEmpty()) {
            throw new Exception("You have problem in RandomValuesServiceImpl.getRandomCar() " +
                    "couldn't retrieve value");
        }

        return car.get();
    }

    @Override
    public int getDiscount() {
        Random random = new Random();
        int i = random.nextInt(7);
        int[] discountsParams = {0, 5, 10, 15, 20, 30, 40, 50};
        return discountsParams[i];
    }

    @Override
    public int getUnderAgeDiscount() {
        return 5;
    }
}
