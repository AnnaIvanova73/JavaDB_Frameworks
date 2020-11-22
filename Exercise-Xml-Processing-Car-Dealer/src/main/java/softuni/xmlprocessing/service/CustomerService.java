package softuni.jsonprocessing.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CustomerService {

    void seedCustomer() throws IOException, JAXBException;

    String getCustomersByOrder() throws JAXBException;
    String executeQuery5() throws JAXBException;

}
