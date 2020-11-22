package softuni.shopxml.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.shopxml.domain.dto.export.query3.CategoryExportDtoQuery3;
import softuni.shopxml.domain.dto.export.query3.CategoryRootExportDtoQuery3;
import softuni.shopxml.domain.dto.imp.category.CategoryImportDto;
import softuni.shopxml.domain.dto.imp.category.CategoryRootImportDto;
import softuni.shopxml.domain.entities.Category;
import softuni.shopxml.domain.entities.Product;
import softuni.shopxml.domain.repo.CategoryRepository;
import softuni.shopxml.domain.repo.ProductRepository;
import softuni.shopxml.services.CategoryService;
import softuni.shopxml.util.validator.ValidatorUtil;
import softuni.shopxml.util.xmlparser.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final static String CATEGORY_IMPORT_PATH = "src/main/resources/xml-import/categories.xml";
    private final static String CATEGORY_EXPORT_PATH_QUERY2 = "src/main/resources/xml-export/query3.xml";

    private final StringBuilder sb;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryServiceImpl(StringBuilder sb,
                               ModelMapper modelMapper,
                               ValidatorUtil validatorUtil,
                               XmlParser xmlParser,
                               CategoryRepository categoryRepository,
                               ProductRepository productRepository) {
        this.sb = sb;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public String seedCategories() throws JAXBException {
        this.sb.setLength(0);

        CategoryRootImportDto rootDto = this.xmlParser.
                parseXml(CategoryRootImportDto.class, CATEGORY_IMPORT_PATH);


        for (CategoryImportDto categoryDto : rootDto.getCategories()) {
            if (!this.validatorUtil.isValid(categoryDto)) {
                this.validatorUtil.violations(categoryDto).forEach(err -> {
                    this.sb.append(err.getMessage())
                            .append(" ")
                            .append(String.format(
                                    "Violations for Category --> Name: %s", categoryDto.getName())
                            ).append(System.lineSeparator());
                });
            }

            Category categoryEntity = this.modelMapper.map(categoryDto, Category.class);
            categoryEntity.setProducts(generateCategoriesRandomly());
            this.categoryRepository.saveAndFlush(categoryEntity);
            this.sb.append(String.format("Successfully added Category --> Name: %s",
                    categoryEntity.getName()))
                    .append(System.lineSeparator());
        }



        return this.sb.toString().trim();
    }

    @Override
    public String executeQuery3() throws JAXBException {
        CategoryRootExportDtoQuery3 root = new CategoryRootExportDtoQuery3();
        List<CategoryExportDtoQuery3> dtos = new ArrayList<>();

        List<Category> entities = this.categoryRepository.findAllByProductsSize();
        for (Category entity : entities) {
            CategoryExportDtoQuery3 dto
                    = this.modelMapper.map(entity, CategoryExportDtoQuery3.class);
            int productsCount = entity.getProducts().size();
            double sum =
                    entity.getProducts().stream()
                            .mapToDouble(e -> Double.parseDouble(e.getPrice().toString())).sum();

            double avgPrice = sum / (productsCount * 1.0);

            MathContext m = new MathContext(2);
            dto.setAvgPrice(BigDecimal.valueOf(avgPrice).round(m));
            dto.setProductsCounts(productsCount);
            dto.setTotalRevenue(BigDecimal.valueOf(sum).round(m));
            dtos.add(dto);
        }

        root.setCategories(dtos);
        this.xmlParser.exportToXml(root,CategoryRootExportDtoQuery3.class,CATEGORY_EXPORT_PATH_QUERY2);
        return "Query 3 finished";
    }

    private Set<Product> generateCategoriesRandomly() {
        Random random = new Random();
        int numberOfRandomProducts = random.nextInt(4) + 1;
        List<Product> products = this.productRepository.findAll();
        Collections.shuffle(products);
        return new HashSet<>(products).stream().limit(numberOfRandomProducts).collect(Collectors.toSet());
    }


}
