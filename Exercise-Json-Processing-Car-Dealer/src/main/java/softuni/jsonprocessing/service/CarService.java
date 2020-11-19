package softuni.jsonprocessing.service;

import java.io.IOException;

public interface CarService {

    void seedCars() throws Exception;
    String findCarsByMakerToyota() throws IOException;
    String getAllCarsWithParams() throws IOException;

}
