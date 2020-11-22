package softuni.jsonprocessing.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface SupplierService {

    void seedSuppliers() throws IOException, JAXBException;

    String findAllLocalSuppliers() throws JAXBException;

}
