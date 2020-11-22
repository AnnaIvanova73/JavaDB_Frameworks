package softuni.jsonprocessing.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonprocessing.domain.dto.export.query1.CustomerChildExportDto;
import softuni.jsonprocessing.domain.dto.export.query1.CustomerParentExportDto;
import softuni.jsonprocessing.domain.dto.export.query5.CustomerExportDto;
import softuni.jsonprocessing.domain.dto.export.query5.CustomerRootExportDto;
import softuni.jsonprocessing.domain.dto.importDto.customers.CustomerChildImportDto;
import softuni.jsonprocessing.domain.dto.importDto.customers.CustomerRootImportDto;
import softuni.jsonprocessing.domain.entities.Customer;
import softuni.jsonprocessing.domain.entities.Part;
import softuni.jsonprocessing.domain.entities.Sale;
import softuni.jsonprocessing.domain.repo.CustomerRepository;
import softuni.jsonprocessing.service.CustomerService;
import softuni.jsonprocessing.util.xmlparser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_PATH = "src/main/resources/xml-import/customers.xml";
    private static final String EXPORT_PATH_QUERY_1 = "src/main/resources/xml-export/query1.xml";
    private static final String CUSTOMER_EXPORT_PATH_QUERY_5 = "src/main/resources/xml-export/query5.xml";

    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(XmlParser xmlParser,
                               ModelMapper modelMapper,
                               CustomerRepository customerRepository) {
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
    }


    @Override
    public void seedCustomer() throws IOException, JAXBException {

        CustomerRootImportDto rootDto =
                this.xmlParser.parseXml(CustomerRootImportDto.class, CUSTOMER_PATH);

        List<CustomerChildImportDto> customerChildren = rootDto.getCustomers();

        for (CustomerChildImportDto customerChild : customerChildren) {
            Customer customer = this.modelMapper.map(customerChild, Customer.class);
            this.customerRepository.saveAndFlush(customer);
        }
    }

    @Override
    public String getCustomersByOrder() throws JAXBException {
        List<CustomerChildExportDto> child = this.customerRepository.findAllByOrderByBirthDateAscYoungDriverAsc()
                .stream().map(e -> this.modelMapper.map(e, CustomerChildExportDto.class))
                .collect(Collectors.toList());

        CustomerParentExportDto export = new CustomerParentExportDto();
        export.setCustomers(child);

        this.xmlParser.exportToXml(export, CustomerParentExportDto.class,EXPORT_PATH_QUERY_1);

        return "First query done!";
    }

    @Override
    public String executeQuery5() throws JAXBException {

        Set<Customer> allByAtLeastOneCar = this.customerRepository.findAllByAtLeastOneCar();

        List<CustomerExportDto> child = new ArrayList<>();
        for (Customer customer : allByAtLeastOneCar) {
            long countCats = customer.getSales().stream().map(Sale::getCar).count();
            List<List<Part>> collect =
                    customer.getSales().stream()
                            .map(e -> e.getCar().getParts()).collect(Collectors.toList());

            double price = 0;
            for (List<Part> parts : collect) {
                double sum = parts.stream().mapToDouble(e -> Double.parseDouble(e.getPrice().toString())).sum();
                price +=sum;
            }
            CustomerExportDto currentDto = this.modelMapper.map(customer, CustomerExportDto.class);
            currentDto.setBoughtCars((int)countCats);
            currentDto.setSpentMoney(String.format("%.2f",price));
            child.add(currentDto);
        }
        CustomerRootExportDto rootDto = new CustomerRootExportDto();
        rootDto.setCustomers(child);
        this.xmlParser.exportToXml(rootDto, CustomerRootExportDto.class,CUSTOMER_EXPORT_PATH_QUERY_5);
        return "Query 5 ready!";
    }


}
// Transformer transformer = TransformerFactory.newInstance().newTransformer();
//        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
////initialize StreamResult with File object to save to file
//        StreamResult result = new StreamResult(new StringWriter());
//        DOMSource source = new DOMSource(doc);
//        transformer.transform(source, result);
//        String xmlString = result.getWriter().toString();
//        System.out.println(xmlString);