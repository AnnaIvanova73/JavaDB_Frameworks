package softuni.jsonprocessing.service.impl.additional;

import org.springframework.stereotype.Service;
import softuni.jsonprocessing.domain.entities.Car;
import softuni.jsonprocessing.domain.entities.Customer;
import softuni.jsonprocessing.domain.entities.Part;
import softuni.jsonprocessing.domain.entities.Supplier;
import softuni.jsonprocessing.domain.repo.CarRepository;
import softuni.jsonprocessing.domain.repo.CustomerRepository;
import softuni.jsonprocessing.domain.repo.PartRepository;
import softuni.jsonprocessing.domain.repo.SupplierRepository;
import softuni.jsonprocessing.service.RandomValuesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class RandomValuesServiceImpl implements RandomValuesService {

    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SupplierRepository supplierRepository;
    private final PartRepository partRepository;

    public RandomValuesServiceImpl(CarRepository carRepository,
                                   CustomerRepository customerRepository,
                                   SupplierRepository supplierRepository,
                                   PartRepository partRepository) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.supplierRepository = supplierRepository;
        this.partRepository = partRepository;
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
    public int getRandomDiscount() {
        Random random = new Random();
        int i = random.nextInt(7);
        int[] discountsParams = {0, 5, 10, 15, 20, 30, 40, 50};
        return discountsParams[i];
    }

    @Override
    public int getUnderAgeDiscount() {
        return 5;
    }

    @Override
    public Supplier getRandomSupplier() throws Exception {
        Random random = new Random();
        long randomSupplier =
                (long) random.nextInt((int) this.supplierRepository.count() - 1) + 1;

        Optional<Supplier> supplier = this.supplierRepository.findById(randomSupplier);

        if (supplier.isEmpty()) {
            throw new Exception("Supplier don't exits");
        }

        return supplier.get();
    }


    @Override
    public List<Part> getRandomParts() throws Exception {
        Random random = new Random();
        int lowerBound = 10;
        int upperBound = 20;

        List<Part> parts = new ArrayList<>();

        int bound = random.nextInt(upperBound - lowerBound) + lowerBound;
        for (int i = 0; i < bound; i++) {
            Part part = getRandomPart();
            parts.add(part);
        }

        return parts;
    }


    private Part getRandomPart() throws Exception {
        Random random = new Random();
        long randomId = (long) random.nextInt((int) this.partRepository.count() - 1) + 1;

        Optional<Part> part = this.partRepository.findById(randomId);

        if (part.isEmpty()) {
            throw new Exception("Invalid part id");
        }

        return part.get();
    }

}
