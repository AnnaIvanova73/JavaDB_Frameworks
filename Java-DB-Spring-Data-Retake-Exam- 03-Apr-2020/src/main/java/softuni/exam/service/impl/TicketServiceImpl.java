package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dtos.xmls.ticketsImport.TicketImportDto;
import softuni.exam.models.dtos.xmls.ticketsImport.TicketRootImportDto;
import softuni.exam.models.entities.Passenger;
import softuni.exam.models.entities.Plane;
import softuni.exam.models.entities.Ticket;
import softuni.exam.models.entities.Town;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.xmlparser.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {
    private final static String TICKETS_PATH = "src/main/resources/files/xml/tickets.xml";


    private final XmlParser xmlParser;
    private final StringBuilder sb;
    private final ModelMapper modelMapper;
    private final TicketRepository ticketRepository;
    private final ValidationUtil validationUtil;
    private final PlaneRepository planeRepository;
    private final TownRepository townRepository;
    private final PassengerRepository passengerRepository;

    public TicketServiceImpl(XmlParser xmlParser,
                             StringBuilder sb,
                             ModelMapper modelMapper,
                             TicketRepository ticketRepository,
                             ValidationUtil validationUtil,
                             PlaneRepository planeRepository,
                             TownRepository townRepository,
                             PassengerRepository passengerRepository) {
        this.xmlParser = xmlParser;
        this.sb = sb;
        this.modelMapper = modelMapper;
        this.ticketRepository = ticketRepository;
        this.validationUtil = validationUtil;
        this.planeRepository = planeRepository;
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
    }


    @Override
    public boolean areImported() {
        return this.ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {

        return String.join("", Files.readAllLines(Path.of(TICKETS_PATH)));
    }

    @Override
    @Transactional
    public String importTickets() throws JAXBException {
        this.sb.setLength(0);

        TicketRootImportDto rootDto
                = this.xmlParser.parseXml(TicketRootImportDto.class, TICKETS_PATH);

        for (TicketImportDto dtoTicket : rootDto.getTickets()) {

            System.out.println("plane");
            Optional<Plane> plane = this.planeRepository.
                    findByRegisterNumber(dtoTicket.getPlane().getRegisteredNumber());
            System.out.println("pass");
            Optional<Passenger> passenger =
                    this.passengerRepository.findByEmailLike(dtoTicket.getPassenger().getEmail());
            System.out.println("to");
            Optional<Town> toTown = this.townRepository.findByName(dtoTicket.getToTown().getName());
            System.out.println("from");
            Optional<Town> fromTown = this.townRepository.findByName(dtoTicket.getFromTown().getName());

            if (!this.validationUtil.isValid(dtoTicket)
                    || plane.isEmpty() || passenger.isEmpty() || toTown.isEmpty() || fromTown.isEmpty()
            ) {
                this.sb.append("Invalid Ticket").append(System.lineSeparator());
                continue;
            }
            Ticket ticketEntity = this.modelMapper.map(dtoTicket, Ticket.class);
            ticketEntity.setPlane(plane.get());
            ticketEntity.setPassenger(passenger.get());
            ticketEntity.setToTown(toTown.get());
            ticketEntity.setFromTown(fromTown.get());
            this.ticketRepository.saveAndFlush(ticketEntity);
            this.sb.append(String.format("Successfully imported Ticket %s - %s",ticketEntity.getFromTown().getName()
                    ,ticketEntity.getToTown().getName()))
                    .append(System.lineSeparator());
        }


        return this.sb.toString().trim();
    }
}
