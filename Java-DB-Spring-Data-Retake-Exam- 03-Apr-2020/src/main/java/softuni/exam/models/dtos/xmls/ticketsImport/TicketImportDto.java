package softuni.exam.models.dtos.xmls.ticketsImport;

import org.hibernate.validator.constraints.Length;
import softuni.exam.config.LocalDateTimeAdapter;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketImportDto {

    @XmlElement(name = "serial-number")
    private String serialNumber;
    @XmlElement
    private BigDecimal price;
    @XmlElement(name = "take-off")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime takeoff;
    @XmlElement(name = "from-town")
    private FromTownImportDto fromTown;
    @XmlElement(name = "to-town")
    private ToTownImportDto toTown;
    @XmlElement(name = "passenger")
    private PassengerTicketImportDto passenger;
    @XmlElement(name = "plane")
    private PlaneTicketImportDto plane;

    public TicketImportDto() {
    }

    @Length(min = 2)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getTakeoff() {
        return takeoff;
    }

    public void setTakeoff(LocalDateTime takeoff) {
        this.takeoff = takeoff;
    }

    public FromTownImportDto getFromTown() {
        return fromTown;
    }

    public void setFromTown(FromTownImportDto fromTown) {
        this.fromTown = fromTown;
    }

    public ToTownImportDto getToTown() {
        return toTown;
    }

    public void setToTown(ToTownImportDto toTown) {
        this.toTown = toTown;
    }

    public PassengerTicketImportDto getPassenger() {
        return passenger;
    }

    public void setPassenger(PassengerTicketImportDto passenger) {
        this.passenger = passenger;
    }

    public PlaneTicketImportDto getPlane() {
        return plane;
    }

    public void setPlane(PlaneTicketImportDto plane) {
        this.plane = plane;
    }
}
