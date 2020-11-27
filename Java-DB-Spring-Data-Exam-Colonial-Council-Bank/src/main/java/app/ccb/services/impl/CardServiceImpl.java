package app.ccb.services.impl;

import app.ccb.domain.dtos.xmls.CardImportDto;
import app.ccb.domain.dtos.xmls.CardRootImportDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Card;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.CardRepository;
import app.ccb.services.CardService;
import app.ccb.util.fileutil.FileUtil;
import app.ccb.util.validation.ValidationUtil;
import app.ccb.util.xmlparser.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Optional;

import static app.ccb.config.constants.ConstantMsg.INVALID_MSG;

@Service
public class CardServiceImpl implements CardService {

    private final static String CARD_PATH = "F:\\SpringData\\Java-DB-Spring-Data-Exam-ColonialCouncilBank\\ColonialCouncilBank\\src\\main\\resources\\files\\xml\\cards.xml";


    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final StringBuilder sb;
    private final ValidationUtil validationUtil;
    private final BankAccountRepository bankAccountRepository;
    private final CardRepository cardRepository;

    public CardServiceImpl(FileUtil fileUtil,
                           XmlParser xmlParser,
                           ModelMapper modelMapper,
                           StringBuilder sb,
                           ValidationUtil validationUtil,
                           BankAccountRepository bankAccountRepository,
                           CardRepository cardRepository) {
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.sb = sb;
        this.validationUtil = validationUtil;
        this.bankAccountRepository = bankAccountRepository;

        this.cardRepository = cardRepository;
    }


    @Override
    public Boolean cardsAreImported() {
      return this.cardRepository.count() > 0 ;
    }

    @Override
    public String readCardsXmlFile() throws IOException {
        return this.fileUtil.readFile(CARD_PATH);
    }

    @Override
    public String importCards() throws JAXBException {
        this.sb.setLength(0);

        CardRootImportDto rootDto = this.xmlParser.parseXml(CardRootImportDto.class, CARD_PATH);

        for (CardImportDto dto : rootDto.getCards()) {
            Optional<BankAccount> bankAccount = this.bankAccountRepository.findFirstByAccountNumber(dto.getBankAccount());
            if (!this.validationUtil.isValid(dto) || bankAccount.isEmpty()) {
                this.sb.append(INVALID_MSG).append(System.lineSeparator());
                continue;
            }

            Card cardEntity = this.modelMapper.map(dto, Card.class);
            cardEntity.setCardNumber(dto.getCardNumber());
            cardEntity.setBankAccount(bankAccount.get());
            this.cardRepository.saveAndFlush(cardEntity);
            System.out.println();
            this.sb.append(String.format("Successfully imported %s - %s.",
                    cardEntity.getClass().getSimpleName(),
                    cardEntity.getCardNumber())).append(System.lineSeparator());
        }

        return this.sb.toString().trim();
    }
}
