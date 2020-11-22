package softuni.jsonprocessing.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.jsonprocessing.service.*;

import java.io.BufferedReader;

@Component
public class CmdRunner implements CommandLineRunner {
    private static final String MSG_OPTIONS = "Enter 0 for seed DB, Enter between 1 and 6 for queries, Enter EXIT";


    private final BufferedReader bufferedReader;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;

    public CmdRunner(BufferedReader bufferedReader,
                     SupplierService supplierService,
                     PartService partService,
                     CarService carService,
                     CustomerService customerService, SaleService saleService) {
        this.bufferedReader = bufferedReader;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(MSG_OPTIONS);
        String line;
        while (!(line = bufferedReader.readLine()).equalsIgnoreCase("exit")) {

            switch (line) {
                case "0":
                    seedDB();
                    break;
                case "1":
                    System.out.println(this.customerService.getCustomersByOrder());
                    break;
                case "2":
                    System.out.println(this.carService.findAllCarsByMakerToyota());
                    break;
                case "3":
                    System.out.println(this.supplierService.findAllLocalSuppliers());
                    break;
                case "4":
                    System.out.println(this.carService.getCarWithParts());
                    break;
                case "5":
                    System.out.println(this.customerService.executeQuery5());
                    break;
                case "6":
                    System.out.println(this.saleService.executeQuery6());
                    break;
                default:
                    System.out.println("No such task");
                    break;
            }
            System.out.println(MSG_OPTIONS);
        }

        System.exit(0);
    }

    private void seedDB() throws Exception {
        System.out.println("That will take sometime...");
        this.supplierService.seedSuppliers();
        this.partService.seedParts();
        this.carService.seedCars();
        this.customerService.seedCustomer();
        this.saleService.seedSales();
        System.err.println("INSERT DONE");
    }
}
