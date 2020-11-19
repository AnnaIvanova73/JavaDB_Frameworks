package softuni.jsonprocessing.service;

import java.io.IOException;

public interface SaleService {
    void seedSales() throws Exception;

    String getAllSales () throws IOException;
}
