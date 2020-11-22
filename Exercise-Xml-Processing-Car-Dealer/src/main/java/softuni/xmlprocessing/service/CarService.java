package softuni.jsonprocessing.service;

import javax.xml.bind.JAXBException;

public interface CarService {

    void seedCars() throws Exception;

    String findAllCarsByMakerToyota() throws JAXBException;
    String getCarWithParts() throws JAXBException;

}
