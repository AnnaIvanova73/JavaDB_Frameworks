package softuni.shopxml.services;

import javax.xml.bind.JAXBException;

public interface UserService {

    String seedUsers() throws JAXBException;

    String executeQuery2() throws JAXBException;
    String executeQuery4() throws JAXBException;
}
