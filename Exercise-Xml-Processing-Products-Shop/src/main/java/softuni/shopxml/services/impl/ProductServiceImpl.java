package softuni.shopxml.services.impl;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.shopxml.domain.dto.export.query1.ProductExportDtoQuery1;
import softuni.shopxml.domain.dto.export.query1.ProductRootExportDtoQuery1;
import softuni.shopxml.domain.dto.imp.product.ProductImportDto;
import softuni.shopxml.domain.dto.imp.product.ProductRootImportDto;
import softuni.shopxml.domain.entities.Product;
import softuni.shopxml.domain.entities.User;
import softuni.shopxml.domain.repo.ProductRepository;
import softuni.shopxml.domain.repo.UserRepository;
import softuni.shopxml.services.ProductService;
import softuni.shopxml.util.validator.ValidatorUtil;
import softuni.shopxml.util.xmlparser.XmlParser;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {

    private final static String PRODUCT_IMPORT_PATH = "src/main/resources/xml-import/products.xml";
    private final static String PRODUCT_EXPORT_PATH_QUERY_1 = "src/main/resources/xml-export/query1.xml";

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;
    private final StringBuilder sb;
    private final UserRepository userRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              ModelMapper modelMapper,
                              XmlParser xmlParser,
                              ValidatorUtil validatorUtil,
                              StringBuilder sb,
                              UserRepository userRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
        this.sb = sb;
        this.userRepository = userRepository;
    }

    @Override
    public String seedProduct() throws Exception {
        this.sb.setLength(0);
        ProductRootImportDto rootDto =
                this.xmlParser.parseXml(ProductRootImportDto.class, PRODUCT_IMPORT_PATH);

        int counter = 0;
        for (ProductImportDto productDto : rootDto.getProducts()) {

            if (!this.validatorUtil.isValid(productDto)) {
                this.validatorUtil.violations(productDto)
                        .forEach(err -> {
                            this.sb.append(err.getMessage()).append(" ")
                                    .append(String.format("Violations for Product  --> %s", productDto.getName()))
                                    .append(System.lineSeparator());
                        });
                continue;
            }

            Product product = this.modelMapper.map(productDto, Product.class);


            if (counter % 2 != 0) {
                if (counter % 11 == 0) {
                    counter++;
                    continue;
                }
                product.setBuyer(getRandomUser());
            }

            product.setSeller(getRandomUser());
            this.productRepository.saveAndFlush(product);

            this.sb.append(String.format("Successfully added Product --> Name: %s Price: %s ",
                    product.getName(),
                    product.getPrice()))
                    .append(System.lineSeparator());
            counter++;
        }

        return sb.toString().trim();
    }

    @Override
    public String executeQuery1() throws JAXBException {
        ProductRootExportDtoQuery1 rootDto = new ProductRootExportDtoQuery1();
        List<ProductExportDtoQuery1> dtos = new ArrayList<>();

        List<Product> entities = this.productRepository.findAllWithPriceInRange();

        for (Product productEntity : entities) {
            ProductExportDtoQuery1 dtoProduct =
                    this.modelMapper.map(productEntity, ProductExportDtoQuery1.class);
            String seller = dtoProduct.getSellerFirstName() == null
                    ? String.format("%s", dtoProduct.getSellerLastName())
                    : String.format("%s %s"
                    , dtoProduct.getSellerFirstName()
                    , dtoProduct.getSellerLastName());
            dtoProduct.setSeller(seller);
            dtos.add(dtoProduct);
        }
        rootDto.setProducts(dtos);
        this.xmlParser
                .exportToXml(rootDto, ProductRootExportDtoQuery1.class, PRODUCT_EXPORT_PATH_QUERY_1);

        return "Query 1 finished";
    }


    private User getRandomUser() throws Exception {
        Random random = new Random();

        long randomId = random.nextInt((int) this.userRepository.count() - 1) + 1;

        Optional<User> byId = this.userRepository.findById(randomId);

        if (byId.isEmpty()) {
            throw new Exception("Invalid user id!");
        }
        return byId.get();
    }

    @Override
    public boolean hasBuyer (Product product){

        return product.getBuyer() != null;

    }
}
