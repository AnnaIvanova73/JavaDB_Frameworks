package softuni.jsonprocessing.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonprocessing.domain.dto.export.query6.CarExportDtoQuery6;
import softuni.jsonprocessing.domain.dto.export.query6.CustomerExportDtoQuery6;
import softuni.jsonprocessing.domain.dto.export.query6.SalesExportDtoQuery6;
import softuni.jsonprocessing.domain.entities.Car;
import softuni.jsonprocessing.domain.entities.Customer;
import softuni.jsonprocessing.domain.entities.Sale;
import softuni.jsonprocessing.domain.repo.SaleRepository;
import softuni.jsonprocessing.service.RandomValuesService;
import softuni.jsonprocessing.service.SaleService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


@Service
public class SaleServiceImpl implements SaleService {

    private static final String SALES_EXPORT_PATH_QUERY_6 = "src/main/resources/json-export/query6.json";
    private final SaleRepository saleRepository;
    private final RandomValuesService randomValuesService;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository,
                           RandomValuesService randomValuesService,
                           ModelMapper modelMapper,
                           Gson gson) {
        this.saleRepository = saleRepository;
        this.randomValuesService = randomValuesService;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedSales() throws Exception {
        int fiveSales = 5;
        for (int i = 0; i < fiveSales; i++) {
            Sale sale = new Sale();
            Customer randomCustomer = this.randomValuesService.getRandomCustomer();
            double discount = this.randomValuesService.getDiscount();

            if (randomCustomer.isYoungDriver()) {
                discount += this.randomValuesService.getUnderAgeDiscount();
            }

            sale.setDiscount((int) discount);
            sale.setCustomer(randomCustomer);
            sale.setCar(this.randomValuesService.getRandomCar());

            this.saleRepository.saveAndFlush(sale);
        }
    }

    @Override
    public String getAllSales() throws IOException {
        List<Sale> all = this.saleRepository.findAll();


        List<SalesExportDtoQuery6> sales = new ArrayList<>();
        for (Sale sale : all) {
            SalesExportDtoQuery6 view = this.modelMapper.map(sale, SalesExportDtoQuery6.class);


            Customer customer = sale.getCustomer();
            String customerName = customer.getName();



            double discount = (sale.getDiscount() * 1.0 / 100);
            double priceWithoutDiscount = sale.getCar().getParts().stream()
                    .mapToDouble(e -> e.getPrice().doubleValue()).sum();
            double totalDiscount = priceWithoutDiscount * discount;
            double priceWithDiscount = priceWithoutDiscount - totalDiscount;


            CustomerExportDtoQuery6 customerExportDtoQuery6 = new CustomerExportDtoQuery6();
            customerExportDtoQuery6.setName(customerName);
            customerExportDtoQuery6.setDiscount(discount);
            customerExportDtoQuery6.setPrice(priceWithoutDiscount);
            customerExportDtoQuery6.setPriceWithDiscount(priceWithDiscount);
            view.setCustomer(customerExportDtoQuery6);

            Car car = sale.getCar();
            String model = car.getModel();
            String make = car.getMake();
            Long travelledDistance = car.getTravelledDistance();

            CarExportDtoQuery6 carExport = new CarExportDtoQuery6();
            carExport.setMake(make);
            carExport.setModel(model);
            carExport.setTravelledDistance(travelledDistance);
            view.setCar(carExport);


            sales.add(view);
        }

        System.out.println(this.gson.toJson(sales));


        Writer writer = new FileWriter(SALES_EXPORT_PATH_QUERY_6);
        this.gson.toJson(sales,writer);

        writer.flush();
        writer.close();
        return "Done go to resources/json-export/query6.json";
    }
}
