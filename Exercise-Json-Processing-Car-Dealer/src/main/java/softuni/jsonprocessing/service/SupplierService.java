package softuni.jsonprocessing.service;

import java.io.IOException;

public interface SupplierService {

    void seedSuppliers() throws IOException;

    String findAllLocalSuppliers () throws IOException;
}
