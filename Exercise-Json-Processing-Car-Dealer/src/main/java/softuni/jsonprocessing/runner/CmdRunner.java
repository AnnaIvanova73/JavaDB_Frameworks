package softuni.jsonprocessing.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.jsonprocessing.service.*;

import java.io.BufferedReader;

@Component
public class CmdRunner implements CommandLineRunner {

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

    // this.customerService.getAllCustomersInOrder();
//        this.carService.findCarsByMakerToyota();
//        this.supplierService.findAllLocalSuppliers();
//        this.carService.getAllCarsWithParams();
//        this.customerService.getAllCustomersNotNull();
//        this.saleService.getAllSales();
    @Override
    public void run(String... args) throws Exception {
        System.out.println(String.format(
                "Hey, choose option:%n" +
                        "Enter 0 for seed DB, bear in mind that i put randomly 10 to 20 parts,%n" +
                        "that can be heavy, if you want to change it go to --> CarServiceImpl.getRandomParts()%n" +
                        "Enter between 1 and 6 for queries, you can find them in resources also i print them on the console%n" +
                        "Enter EXIT"
        ));

        String text = "Enter 0 for seed DB, Enter between 1 and 6 for queries, Enter EXIT";
        String line;
        while(!(line = bufferedReader.readLine()).equalsIgnoreCase("exit")){


            switch (line){
                case "0":
                    seedDB();
                    System.out.println("INSERT DONE");
                    break;
                case "1":
                    System.out.println(this.customerService.getAllCustomersInOrder());
                    System.out.println(text);
                    break;
                case "2":
                    System.out.println(this.carService.findCarsByMakerToyota());
                    System.out.println(text);
                    break;
                case "3":
                    System.out.println(this.supplierService.findAllLocalSuppliers());
                    System.out.println(text);
                    break;
                case "4":
                    System.out.println(this.carService.getAllCarsWithParams());
                    System.out.println(text);
                    break;
                case "5":
                    System.out.println(this.customerService.getAllCustomersNotNull());
                    System.out.println(text);
                    break;
                case "6":
                    System.out.println(this.saleService.getAllSales());
                    System.out.println(text);
                    break;
            }
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
