package softuni.jsonprocessing.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.jsonprocessing.model.dto.export.query3.CategoryExportDto;
import softuni.jsonprocessing.model.dto.importDto.CategoryImportDto;
import softuni.jsonprocessing.model.entities.Category;
import softuni.jsonprocessing.model.entities.Product;
import softuni.jsonprocessing.repository.CategoryRepository;
import softuni.jsonprocessing.repository.ProductRepository;
import softuni.jsonprocessing.service.CategoryService;
import softuni.jsonprocessing.util.ValidatorUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final static String CATEGORY_IMPORT_PATH = "src/main/resources/json-import/categories.json";
    private final static String CATEGORY_EXPORT_PATH_QUERY_3 = "src/main/resources/json-export/query3.json";

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final StringBuilder sb;

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(Gson gson,
                               ModelMapper modelMapper,
                               ValidatorUtil validatorUtil,
                               StringBuilder sb,
                               CategoryRepository categoryRepository,
                               ProductRepository productRepository) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.sb = sb;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public String seedCategory() throws IOException {
        this.sb.setLength(0);
        String source = String.join("",
                Files.readAllLines(Path.of(CATEGORY_IMPORT_PATH)));

        CategoryImportDto[] categoryImportDtos = this.gson.fromJson(source, CategoryImportDto[].class);

        for (CategoryImportDto categoryDto : categoryImportDtos) {

            if (!this.validatorUtil.isValid(categoryDto)) {
                this.validatorUtil.violations(categoryDto)
                        .forEach(err -> {
                            sb.append(err.getMessage())
                                    .append(String.format("\tInput name: %s", categoryDto.getName()));
                        });

            } else {
                categoryDto.setProducts(getRandomSetOfProducts());
                this.categoryRepository.saveAndFlush(this.modelMapper.map(categoryDto, Category.class));
                sb.append(String.format("Successfully" +
                        " added category with name: %s", categoryDto.getName()))
                        .append(System.lineSeparator());
            }

            categoryDto.setProducts(getRandomSetOfProducts());
        }
        return this.sb.toString().trim();
    }

    @Override
    public String executeQuery3() throws IOException {
        List<Category> in = this.categoryRepository.productByCount();
        List<CategoryExportDto> export = new ArrayList<>();
        for (Category category : in) {


            Set<Product> products = category.getProducts();
            BigDecimal productsCount = new BigDecimal(products.size());
            BigDecimal totalRevenue = BigDecimal.ZERO;
            for (Product product : products) {
                totalRevenue = totalRevenue.add(product.getPrice());
            }

            BigDecimal avg = totalRevenue.divide(productsCount, 6, RoundingMode.CEILING);

            CategoryExportDto categoryExportDto = new CategoryExportDto(category.getName(),
                    productsCount,avg,totalRevenue);

            export.add(categoryExportDto);
        }

        System.out.println(this.gson.toJson(export));
        Writer writer = new FileWriter(CATEGORY_EXPORT_PATH_QUERY_3);
        this.gson.toJson(export,writer);
        writer.flush();
        writer.close();
        return "Query 3 ready";
    }

    private Set<Product> getRandomSetOfProducts() {
        Random random = new Random();
        int numberOfRandomProducts = random.nextInt(4) + 1;
        List<Product> products = this.productRepository.findAll();
        Collections.shuffle(products);
        return new HashSet<>(products).stream().limit(numberOfRandomProducts).collect(Collectors.toSet());
    }
}


