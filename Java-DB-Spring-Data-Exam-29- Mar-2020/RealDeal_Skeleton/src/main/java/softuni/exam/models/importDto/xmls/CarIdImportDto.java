package softuni.exam.models.importDto.xmls;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarIdImportDto {

    @XmlElement(name = "id")
    private Integer carId;

    public CarIdImportDto() {
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }
}
