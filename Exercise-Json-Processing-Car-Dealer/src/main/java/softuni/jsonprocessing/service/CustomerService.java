package softuni.jsonprocessing.service;

import java.io.IOException;
import java.text.ParseException;

public interface CustomerService {

    void seedCustomer() throws IOException;

    String getAllCustomersInOrder() throws IOException, ParseException;
    String getAllCustomersNotNull () throws IOException;

}
