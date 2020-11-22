package softuni.jsonprocessing.domain.dto.export.query2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarRootExportDto {

    @XmlElement(name = "car")
    private List<CarChildExportDto> cars;


    public CarRootExportDto() {
    }

    public List<CarChildExportDto> getCars() {
        return cars;
    }

    public void setCars(List<CarChildExportDto> cars) {
        this.cars = cars;
    }
}
