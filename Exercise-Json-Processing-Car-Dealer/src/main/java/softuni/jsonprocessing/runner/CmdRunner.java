package softuni.jsonprocessing.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.jsonprocessing.service.*;

import java.io.BufferedReader;

@Component
public class CmdRunner implements CommandLineRunner {
    private static final String INITIAL_MSG_INFO = "Select option:\r\n" +
            "Enter 0 -> seed DB, bear in mind that i put randomly 10 to 20 parts,\n" +
            "that can be heavy, if you want to change it go to --> CarServiceImpl.getRandomParts()\n" +
            "Enter -> 1 and 6 for queries, you can find them in resources also i print them on the console" +
            "Enter EXIT";
    private static final String MSG_OPTIONS = "Enter 0 for seed DB, Enter between 1 and 6 for queries, Enter EXIT";

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final BufferedReader bufferedReader;

    public CmdRunner(SupplierService supplierService,
                     PartService partService,
                     CarService carService,
                     CustomerService customerService,
                     SaleService saleService,
                     BufferedReader bufferedReader) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(INITIAL_MSG_INFO);
        System.out.println(MSG_OPTIONS);


        String line;
        while (!(line = bufferedReader.readLine()).equalsIgnoreCase("exit")) {

            switch (line) {
                case "0":
                    seedDB();
                    System.out.println("INSERT DONE");
                    break;
                case "1":
                    System.out.println(this.customerService.getAllCustomersInOrder());

                    break;
                case "2":
                    System.out.println(this.carService.findCarsByMakerToyota());

                    break;
                case "3":
                    System.out.println(this.supplierService.findAllLocalSuppliers());

                    break;
                case "4":
                    System.out.println(this.carService.getAllCarsWithParams());

                    break;
                case "5":
                    System.out.println(this.customerService.getAllCustomersNotNull());

                    break;
                case "6":
                    System.out.println(this.saleService.getAllSales());

                    break;
                default:
                    System.out.println("No such task");
                    break;
            }
            System.out.println(MSG_OPTIONS);

        }

    }

    private void seedDB() throws Exception {
        this.supplierService.seedSuppliers();
        this.partService.seedParts();
        this.carService.seedCars();
        this.customerService.seedCustomer();
        this.saleService.seedSales();
    }
}
