package app.ccb.services.impl;

import app.ccb.domain.dtos.xmls.BankAccountImportDto;
import app.ccb.domain.dtos.xmls.BankAccountRootImportDto;
import app.ccb.domain.entities.BankAccount;
import app.ccb.domain.entities.Client;
import app.ccb.repositories.BankAccountRepository;
import app.ccb.repositories.ClientRepository;
import app.ccb.services.BankAccountService;
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
public class BankAccountServiceImpl implements BankAccountService {


    private final static String BANK_ACCOUNT_PATH = "F:\\SpringData\\Java-DB-Spring-Data-Exam-ColonialCouncilBank\\ColonialCouncilBank\\src\\main\\resources\\files\\xml\\bank-accounts.xml";


    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final StringBuilder sb;
    private final ValidationUtil validationUtil;
    private final BankAccountRepository bankAccountRepository;
    private final ClientRepository clientRepository;

    public BankAccountServiceImpl(FileUtil fileUtil,
                                  XmlParser xmlParser,
                                  ModelMapper modelMapper,
                                  StringBuilder sb,
                                  ValidationUtil validationUtil,
                                  BankAccountRepository bankAccountRepository,
                                  ClientRepository clientRepository) {
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.sb = sb;
        this.validationUtil = validationUtil;
        this.bankAccountRepository = bankAccountRepository;
        this.clientRepository = clientRepository;
    }


    @Override
    public Boolean bankAccountsAreImported() {
        return this.bankAccountRepository.count() != 0;
    }

    @Override
    public String readBankAccountsXmlFile() throws IOException {
        return this.fileUtil.readFile(BANK_ACCOUNT_PATH);
    }

    @Override
    public String importBankAccounts() throws JAXBException {
        this.sb.setLength(0);
        BankAccountRootImportDto rootDto = this.xmlParser.parseXml(BankAccountRootImportDto.class, BANK_ACCOUNT_PATH);

        for (BankAccountImportDto dto : rootDto.getBankAccounts()) {
            Optional<Client> client = this.clientRepository.findFirstByFullName(dto.getClientName());
            if (!this.validationUtil.isValid(dto) || client.isEmpty()) {
                this.sb.append(INVALID_MSG).append(System.lineSeparator());
                continue;
            }
            BankAccount baEntity = this.modelMapper.map(dto, BankAccount.class);
            baEntity.setClient(client.get());
            this.bankAccountRepository.saveAndFlush(baEntity);
            client.get().setBankAccount(baEntity);
            this.clientRepository.saveAndFlush(client.get());
            this.sb.append(String.format("Successfully imported %s - %s.",
                    baEntity.getClass().getSimpleName(),
                    baEntity.getAccountNumber())).append(System.lineSeparator());
        }

        return this.sb.toString().trim();
    }
}
