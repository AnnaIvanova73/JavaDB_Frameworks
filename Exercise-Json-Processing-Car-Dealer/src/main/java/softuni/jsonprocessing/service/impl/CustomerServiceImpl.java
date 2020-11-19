package softuni.jsonprocessing.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonprocessing.domain.dto.export.CustomerExportDto;
import softuni.jsonprocessing.domain.dto.export.query5.SalesViewDto;
import softuni.jsonprocessing.domain.dto.importDto.CustomerDtoImport;
import softuni.jsonprocessing.domain.entities.Customer;
import softuni.jsonprocessing.domain.entities.Part;
import softuni.jsonprocessing.domain.entities.Sale;
import softuni.jsonprocessing.domain.repo.CustomerRepository;
import softuni.jsonprocessing.service.CustomerService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_PATH = "src/main/resources/json/customers.json";
    private static final String EXPORT_PATH_QUERY_1 = "src/main/resources/json-export/query1.json";
    private static final String CUSTOMER_EXPORT_PATH_QUERY_5 = "src/main/resources/json-export/query5.json";

    private final CustomerRepository customerRepository;

    private final Gson gson;
    private final ModelMapper modelMapper;


    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               Gson gson,
                               ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedCustomer() throws IOException {
        String source = String.join("", Files.readAllLines(Path.of(CUSTOMER_PATH)));

        CustomerDtoImport[] customerDtoImports = this.gson.fromJson(source, CustomerDtoImport[].class);

        for (CustomerDtoImport customerDtoImport : customerDtoImports) {
            Customer customer = this.modelMapper.map(customerDtoImport, Customer.class);
            this.customerRepository.saveAndFlush(customer);

        }

    }

    @Override
    public String getAllCustomersInOrder() throws IOException {
        Writer writer = new FileWriter(EXPORT_PATH_QUERY_1);

        Set<Customer> customers = this.customerRepository.findAllByOrderByBirthDateAscYoungDriverAsc();


        List<CustomerExportDto> export = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerExportDto customerDto = this.modelMapper.map(customer, CustomerExportDto.class);
            customerDto.setBirthDate(customer.getBirthDate().toString());
            export.add(customerDto);
        }

        System.out.println(this.gson.toJson(export));
        this.gson.toJson(export, writer);

        writer.flush();
        writer.close();
        return "File customer-export is created in resources/json-export/query1.json";
    }

    @Override
    public String getAllCustomersNotNull() throws IOException {
        Set<Customer> customers = this.customerRepository.findAllBySalesNotNull();

        Set<SalesViewDto> customersExport = new HashSet<>();
        for (Customer customer : customers) {

            Set<Sale> sales = customer.getSales();
            double finalPrice = 0;
            for (Sale sale : sales) {

                List<Part> parts = sale.getCar().getParts();
                double price = 0;
                for (Part e : parts) {
                    double v = e.getPrice().doubleValue();
                    price += v;
                }
                int discount = sale.getDiscount();
                double percent = discount / 100.00;
                double v = price * percent;
                finalPrice = price - v;

            }

            BigDecimal bigDecimal = new BigDecimal(Double.toString(finalPrice));
            bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
            SalesViewDto dto = this.modelMapper.map(customer,
                    SalesViewDto.class);
            dto.setBoughtCars(customer.getSales().size());
            dto.setName(customer.getName());
            dto.setSpentMoney(bigDecimal);
            customersExport.add(dto);
        }

        List<SalesViewDto> collect = customersExport.stream().sorted((a, b) -> {
            int i = b.getSpentMoney().compareTo(a.getSpentMoney());
            if (i == 0) {
                i = b.getBoughtCars() - a.getBoughtCars();
            }
            return i;
        }).collect(Collectors.toList());

        System.out.println(this.gson.toJson(collect));

        Writer writer = new FileWriter(CUSTOMER_EXPORT_PATH_QUERY_5);

        this.gson.toJson(collect, writer);
        writer.flush();
        writer.close();
        System.out.println();
        return "Done --> resources/json-export/query5.json";
    }
}
