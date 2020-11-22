package softuni.shopxml.services;

import softuni.shopxml.domain.entities.Product;

import javax.xml.bind.JAXBException;

public interface ProductService {

    String seedProduct() throws Exception;

    String executeQuery1() throws JAXBException;

    boolean hasBuyer(Product product);
}
