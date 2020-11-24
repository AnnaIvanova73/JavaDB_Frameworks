package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.entities.Car;
import softuni.exam.models.entities.Offer;
import softuni.exam.models.entities.Seller;
import softuni.exam.models.importDto.xmls.OfferImportDto;
import softuni.exam.models.importDto.xmls.OfferRootImportDto;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.xmlparser.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;


@Service
public class OfferServiceImpl implements OfferService {
    private final static String OFFERS_PATH = "src/main/resources/files/xml/offers.xml";

    private final OfferRepository offerRepository;
    private final StringBuilder sb;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final CarRepository carRepository;
    private final SellerRepository sellerRepository;

    public OfferServiceImpl(OfferRepository offerRepository,
                            StringBuilder sb,
                            ModelMapper modelMapper,
                            ValidationUtil validationUtil,
                            XmlParser xmlParser,
                            CarRepository carRepository,
                            SellerRepository sellerRepository) {
        this.offerRepository = offerRepository;
        this.sb = sb;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.carRepository = carRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {

        return String.join("", Files.readAllLines(Path.of(OFFERS_PATH)));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        sb.setLength(0);
        String invalid = "Invalid offer";
        OfferRootImportDto offerRootImportDto =
                this.xmlParser.parseXml(OfferRootImportDto.class, OFFERS_PATH);

        for (OfferImportDto offer : offerRootImportDto.getOffers()) {

            if (!this.validationUtil.isValid(offer)
                    || offer.getCar().getCarId() == null
                    || offer.getSeller().getSellerId() == null) {
                sb.append(invalid).append(System.lineSeparator());
                continue;
            }

            Offer currentOffer = this.modelMapper.map(offer, Offer.class);

            Car car = this.carRepository.findById(offer.getCar().getCarId()).get();
            Seller seller = this.sellerRepository.findById(offer.getSeller().getSellerId()).get();

            currentOffer.setSeller(seller);
            currentOffer.setCar(car);
            currentOffer.setPictures(new HashSet<>(car.getPictures()));
            this.offerRepository.saveAndFlush(currentOffer);

            sb.append(String.format("Successfully import offer %s - %s",currentOffer.isAddedOn().toString(),
                    currentOffer.isHasGoldStatus())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
