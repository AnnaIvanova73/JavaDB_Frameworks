package softuni.jsonprocessing.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonprocessing.model.dto.export.query1.ProductExportDto;
import softuni.jsonprocessing.model.dto.importDto.ProductImportDto;
import softuni.jsonprocessing.model.entities.Product;
import softuni.jsonprocessing.model.entities.User;
import softuni.jsonprocessing.repository.ProductRepository;
import softuni.jsonprocessing.repository.UserRepository;
import softuni.jsonprocessing.service.ProductService;
import softuni.jsonprocessing.util.ValidatorUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCT_IMPORT_PATH = "src/main/resources/json-import/products.json";
    private static final String PRODUCT_EXPORT_PATH_QUERY_1 = "src/main/resources/json-export/query1.json";
    private final ModelMapper modelMapper;
    private final Gson gson;

    private final ValidatorUtil validatorUtil;
    private final StringBuilder sb;

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductServiceImpl(ModelMapper modelMapper, Gson gson,
                              ProductRepository productRepository,
                              ValidatorUtil validatorUtil,
                              StringBuilder sb,
                              UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.productRepository = productRepository;
        this.validatorUtil = validatorUtil;
        this.sb = sb;
        this.userRepository = userRepository;
    }


    @Override
    public String seedProduct() throws Exception {
        this.sb.setLength(0);

        String source = String.
                join("", Files.readAllLines(Path.of(PRODUCT_IMPORT_PATH)));

        ProductImportDto[] productImports = this.gson.fromJson(source, ProductImportDto[].class);

        int counter = 0;
        for (ProductImportDto productImport : productImports) {

            if (!this.validatorUtil.isValid(productImport)) {
                this.validatorUtil.violations(productImport)
                        .forEach(err -> {
                            sb.append(err.getMessage())
                                    .append(String.format("Error in input!\r\n For input name: %s price: %s",
                                            productImport.getName(), productImport.getPrice()))
                                    .append(System.lineSeparator());
                        });
                continue;
            }

            if (counter % 2 != 0) {
                if (counter % 11 == 0) {
                    counter++;
                    continue;
                }
                productImport.setBuyer(getRandomUser());
            }


            productImport.setSeller(getRandomUser());
            this.productRepository.saveAndFlush(this.modelMapper.map(productImport, Product.class));

            counter++;
            sb.append(String.format("Successfully added product name: %s age: %s",
                    productImport.getName(),
                    productImport.getPrice()))
                    .append(System.lineSeparator());
        }
        return this.sb.toString().trim();
    }

    @Override
    public String executeQuery_1() throws IOException {
        String input = "q";
        List<Product> q = this.productRepository.findAllByNameContainsAndPriceBetweenOrderByPriceAsc(
                input, new BigDecimal("500"), new BigDecimal("1000"));

        List<ProductExportDto> toExport = new ArrayList<>();

        for (Product product : q) {
            ProductExportDto dto = this.modelMapper.map(product, ProductExportDto.class);
            String seller = String.format("%s %s", product.getSeller().getFirstName(), product.getSeller().getLastName());
            dto.setSeller(seller);
            toExport.add(dto);
        }


        System.out.println(this.gson.toJson(toExport));

        Writer writer = new FileWriter(PRODUCT_EXPORT_PATH_QUERY_1);
        this.gson.toJson(toExport, writer);
        writer.flush();
        writer.close();

        String asdd = "Check DB, result differ due to random values:";
        String asd = "\"randomly select the buyer and seller from the existing users. Leave out some products that have not been sold (i.e. buyer is null).\" --> document ";
        return "Query 1 executed is on console and in resources as well" + System.lineSeparator() +
                asdd + System.lineSeparator() + asd;
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
}
