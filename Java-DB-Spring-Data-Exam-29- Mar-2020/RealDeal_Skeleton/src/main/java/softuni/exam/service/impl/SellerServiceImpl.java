package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.entities.Rating;
import softuni.exam.models.entities.Seller;
import softuni.exam.models.importDto.xmls.SellerImportDto;
import softuni.exam.models.importDto.xmls.SellerRootImportDto;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.xmlparser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SellerServiceImpl implements SellerService {

    private final static String SELLERS_PATH = "src/main/resources/files/xml/sellers.xml";
//    private static final String PICTURES_PATH = "src/main/resources/files/json/pictures.json";

    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final SellerRepository sellerRepository;
    private final StringBuilder sb;
    private final ValidationUtil validationUtil;

    public SellerServiceImpl(StringBuilder sb,
                             XmlParser xmlParser,
                             ModelMapper modelMapper,
                             SellerRepository sellerRepository,
                             ValidationUtil validationUtil) {
        this.sb = sb;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.sellerRepository = sellerRepository;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {


        return String.join("", Files.readAllLines(Path.of(SELLERS_PATH)));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {
        this.sb.setLength(0);

        SellerRootImportDto sellerRootImportDto =
                this.xmlParser.parseXml(SellerRootImportDto.class,SELLERS_PATH);

        for (SellerImportDto dto : sellerRootImportDto.getSellers()) {
            Rating rating;
            try {
                rating = Rating.valueOf(dto.getRating().toString());
            } catch (Exception e) {
                sb.append("Invalid seller").append(System.lineSeparator());
                continue;
            }

            if (!this.validationUtil.isValid(dto)) {
                sb.append("Invalid seller").append(System.lineSeparator());
                continue;
            }

            Seller seller = this.modelMapper.map(dto, Seller.class);
            this.sellerRepository.saveAndFlush(seller);
            sb.append(String.format(
                    "Successfully import seller %s - %s",dto.getLastName(),dto.getEmail()))
                    .append(System.lineSeparator());

        }

        return sb.toString().trim();
    }
}
