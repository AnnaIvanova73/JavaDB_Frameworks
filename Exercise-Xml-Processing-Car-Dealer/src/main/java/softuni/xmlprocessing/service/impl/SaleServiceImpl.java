package softuni.jsonprocessing.service.impl;

import org.decimal4j.util.DoubleRounder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonprocessing.domain.dto.export.query6.CarExportDtoQuery6;
import softuni.jsonprocessing.domain.dto.export.query6.SaleExportDtoQuery6;
import softuni.jsonprocessing.domain.dto.export.query6.SaleRootExportDtoQuery6;
import softuni.jsonprocessing.domain.entities.Customer;
import softuni.jsonprocessing.domain.entities.Sale;
import softuni.jsonprocessing.domain.repo.SaleRepository;
import softuni.jsonprocessing.service.RandomValuesService;
import softuni.jsonprocessing.service.SaleService;
import softuni.jsonprocessing.util.xmlparser.XmlParser;

import javax.xml.bind.JAXBException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class SaleServiceImpl implements SaleService {

    private static final String SALES_EXPORT_PATH_QUERY_6 = "src/main/resources/xml-export/query6.xml";
    private final SaleRepository saleRepository;
    private final RandomValuesService randomValuesService;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository,
                           RandomValuesService randomValuesService,
                           ModelMapper modelMapper,
                           XmlParser xmlParser) {
        this.saleRepository = saleRepository;
        this.randomValuesService = randomValuesService;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedSales() throws Exception {
        int randomSales = (int)(Math.random()*(20-10)+10);

        for (int i = 0; i < randomSales; i++) {
            Sale sale = new Sale();
            Customer randomCustomer = this.randomValuesService.getRandomCustomer();
            double discount = this.randomValuesService.getRandomDiscount();

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
    public String executeQuery6() throws JAXBException {

        SaleRootExportDtoQuery6 root = new SaleRootExportDtoQuery6();
        List<SaleExportDtoQuery6> sales = new ArrayList<>();

        List<Sale> all = this.saleRepository.findAll();

        for (Sale sale : all) {
            SaleExportDtoQuery6 dto = this.modelMapper.map(sale, SaleExportDtoQuery6.class);
            double discount = sale.getDiscount() / 100.0;

            double totalSum =
                    sale.getCar()
                            .getParts()
                            .stream()
                            .mapToDouble(p -> Double.parseDouble(p.getPrice().toString())).sum();

            dto.setDiscount(discount);

            double priceDiscount = discount * totalSum;
            double totalPriceWithDiscount = totalSum - priceDiscount;

            dto.setPrice(BigDecimal.valueOf(DoubleRounder.round(totalSum,2)));
            dto.setPriceWithDiscount(BigDecimal.valueOf(DoubleRounder.round(totalPriceWithDiscount,2)));

            CarExportDtoQuery6 carDto = this.modelMapper.map(sale.getCar(), CarExportDtoQuery6.class);
            dto.setCar(carDto);
            sales.add(dto);
        }

        root.setSales(sales);
        this.xmlParser.exportToXml(root,SaleRootExportDtoQuery6.class,SALES_EXPORT_PATH_QUERY_6);
        return "Query 6 done";
    }
}

