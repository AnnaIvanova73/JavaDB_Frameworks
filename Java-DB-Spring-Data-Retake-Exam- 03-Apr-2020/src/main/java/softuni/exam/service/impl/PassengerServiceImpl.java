package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.jsons.PassengerImportDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;


@Service
public class PassengerServiceImpl implements PassengerService {
    private final static String PASSENGER_PATH = "src/main/resources/files/json/passengers.json";


    private final Gson gson;
    private final StringBuilder sb;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;
    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl(Gson gson,
                                StringBuilder sb,
                                ModelMapper modelMapper,
                                ValidationUtil validationUtil,
                                TownRepository townRepository,
                                PassengerRepository passengerRepository) {
        this.gson = gson;
        this.sb = sb;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
    }


    @Override
    public boolean areImported() {
        return this.passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return String.join("", Files.readAllLines(Path.of(PASSENGER_PATH)));
    }

    @Override
    public String importPassengers() throws IOException {
        this.sb.setLength(0);
        PassengerImportDto[] rootDto = this.gson.fromJson(readPassengersFileContent(), PassengerImportDto[].class);
        for (PassengerImportDto currentDto : rootDto) {
            Optional<Town> townByName = this.townRepository.findByName(currentDto.getTown());
            if (!this.validationUtil.isValid(currentDto) || townByName.isEmpty()) {
                this.sb.append("Invalid Passenger").append(System.lineSeparator());
                continue;
            }
            Passenger psg = this.modelMapper.map(currentDto, Passenger.class);
            psg.setTown(townByName.get());
            this.passengerRepository.saveAndFlush(psg);

            this.sb.append(String.format("Successfully imported Passenger %s - %s",
                    psg.getLastName(), psg.getEmail()))
                    .append(System.lineSeparator());

        }
        return this.sb.toString().trim();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        sb.setLength(0);
        Set<Passenger> andOrder = this.passengerRepository.findAndOrder();

        for (Passenger passenger : andOrder) {
            sb.append(String.format("Passenger %s  %s", passenger.getFirstName(), passenger.getLastName()))
                    .append(System.lineSeparator())
                    .append(String.format("\tEmail - %s", passenger.getEmail()))
                    .append(System.lineSeparator())
                    .append(String.format("\tPhone - %s", passenger.getPhoneNumber()))
                    .append(System.lineSeparator())
                    .append(String.format("\tNumber of tickets - %d", passenger.getTickets().size()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
