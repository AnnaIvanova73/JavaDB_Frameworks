package app.ccb.services.impl;

import app.ccb.domain.dtos.jsons.ClientImportDto;
import app.ccb.domain.entities.Client;
import app.ccb.domain.entities.Employee;
import app.ccb.repositories.ClientRepository;
import app.ccb.repositories.EmployeeRepository;
import app.ccb.services.ClientService;
import app.ccb.util.fileutil.FileUtil;
import app.ccb.util.validation.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static app.ccb.config.constants.ConstantMsg.INVALID_MSG;

@Service
public class ClientServiceImpl implements ClientService {

    private final static String CLIENT_PATN =
            "F:\\SpringData\\Java-DB-Spring-Data-Exam-ColonialCouncilBank\\" +
                    "ColonialCouncilBank\\src\\main\\resources\\files\\json\\clients.json";


    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final StringBuilder sb;
    private final ValidationUtil validationUtil;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(FileUtil fileUtil,
                             Gson gson,
                             ModelMapper modelMapper,
                             StringBuilder sb,
                             ValidationUtil validationUtil,
                             EmployeeRepository employeeRepository,
                             ClientRepository clientRepository) {
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.sb = sb;
        this.validationUtil = validationUtil;
        this.employeeRepository = employeeRepository;

        this.clientRepository = clientRepository;
    }

    @Override
    public Boolean clientsAreImported() {
     return this.clientRepository.count() > 0;

    }

    @Override
    public String readClientsJsonFile() throws IOException {
        return this.fileUtil.readFile(CLIENT_PATN);
    }

    @Override
    public String importClients(String clients) throws IOException {
        this.sb.setLength(0);
        ClientImportDto[] clientImportDtos = this.gson.fromJson(readClientsJsonFile(), ClientImportDto[].class);


        for (ClientImportDto clientDto : clientImportDtos) {
            String[] tokens = clientDto.getEmployee().split("\\s+");
            Optional<Set<Employee>> employees = this.employeeRepository
                    .findAllByFirstNameLikeAndLastNameLike(tokens[0],tokens[1]);

            if (!this.validationUtil.isValid(clientDto) || employees.isEmpty()) {
                this.sb.append(INVALID_MSG).append(System.lineSeparator());
                continue;
            }

            String fullName = clientDto.getFirstName() + " " + clientDto.getLastName();
            Client clientEntity = this.modelMapper.map(clientDto, Client.class);
            clientEntity.setFullName(fullName);
            clientEntity.setEmployees(new HashSet<>(employees.get()));
            this.clientRepository.saveAndFlush(clientEntity);
            this.sb.append(String.format("Successfully imported %s - %s.",
                    clientEntity.getClass().getSimpleName(),
                    clientEntity.getFullName())).append(System.lineSeparator());
        }

        return this.sb.toString().trim();
    }

    @Override
    public String exportFamilyGuy() {
        this.sb.setLength(0);
        Set<Client> collect = this.clientRepository.findClientByBankAccountCards().stream()
                .limit(1).collect(Collectors.toSet());

        for (Client client : collect) {
            sb.append("Client name: ")
                    .append(client.getFullName())
                    .append(System.lineSeparator())
                    .append("Client age: ")
                    .append(client.getAge())
                    .append(System.lineSeparator())
                    .append("Bank Account Number: ")
                    .append(client.getBankAccount().getAccountNumber())
                    .append(System.lineSeparator())
                    .append("Cards: ").append(System.lineSeparator());
            client.getBankAccount().getCards().forEach(c -> {
                sb.append("Card number: ")
                        .append(c.getCardNumber())
                        .append(System.lineSeparator())
                        .append("Card status: ")
                        .append(c.getCardStatus()).append(System.lineSeparator());
            });

        }
        return this.sb.toString().trim();
    }
}
