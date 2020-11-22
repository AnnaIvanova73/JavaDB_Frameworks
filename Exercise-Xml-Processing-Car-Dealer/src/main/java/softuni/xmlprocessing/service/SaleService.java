package softuni.jsonprocessing.service;

import javax.xml.bind.JAXBException;

public interface SaleService {
    void seedSales() throws Exception;

    String executeQuery6() throws JAXBException;
}
