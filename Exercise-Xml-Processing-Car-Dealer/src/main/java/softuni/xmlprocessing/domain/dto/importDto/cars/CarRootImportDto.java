package softuni.jsonprocessing.domain.dto.importDto.cars;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarRootImportDto {

    @XmlElement(name = "car")
    private List<CarChildImportDto> cars;

    public CarRootImportDto() {
    }

    public List<CarChildImportDto> getCars() {
        return cars;
    }

    public void setCars(List<CarChildImportDto> cars) {
        this.cars = cars;
    }
}
