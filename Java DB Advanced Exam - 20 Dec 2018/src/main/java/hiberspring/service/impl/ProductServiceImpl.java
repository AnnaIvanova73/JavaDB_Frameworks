package hiberspring.service.impl;

import hiberspring.domain.dtos.xmls.product.ProductImportDto;
import hiberspring.domain.dtos.xmls.product.ProductRootImportDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Product;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.ProductRepository;
import hiberspring.service.ProductService;
import hiberspring.util.fileutil.FileUtil;
import hiberspring.util.validation.ValidationUtil;
import hiberspring.util.xmlparser.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;

import static hiberspring.common.Constants.INCORRECT_DATA_MESSAGE;
import static hiberspring.common.Constants.SUCCESSFUL_IMPORT_MESSAGE;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String PRODUCTS_PATH = "src/main/resources/files/products.xml";

    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final StringBuilder sb;
    private final FileUtil fileUtil;
    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ModelMapper modelMapper,
                              XmlParser xmlParser,
                              ValidationUtil validationUtil,
                              StringBuilder sb, FileUtil fileUtil,
                              BranchRepository branchRepository,
                              ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.sb = sb;
        this.fileUtil = fileUtil;
        this.branchRepository = branchRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Boolean productsAreImported() {
        return this.productRepository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return this.fileUtil.readFile(PRODUCTS_PATH);
    }

    @Override
    public String importProducts() throws JAXBException {
        this.sb.setLength(0);
        ProductRootImportDto rootDto =
                this.xmlParser.parseXml(ProductRootImportDto.class, PRODUCTS_PATH);

        for (ProductImportDto product : rootDto.getProducts()) {
            if (!this.validationUtil.isValid(product)) {
                this.sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            Optional<Branch> byNameLike = this.branchRepository.findByNameLike(product.getBranch());

            if(byNameLike.isEmpty()){
                this.sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            Product productEntity = this.modelMapper.map(product, Product.class);
            productEntity.setBranch(byNameLike.get());
            this.productRepository.saveAndFlush(productEntity);
            this.sb
                    .append(String.format(SUCCESSFUL_IMPORT_MESSAGE,
                            productEntity.getClass().getSimpleName(), productEntity.getName()))
                    .append(System.lineSeparator());
        }


        return this.sb.toString().trim();
    }
}
