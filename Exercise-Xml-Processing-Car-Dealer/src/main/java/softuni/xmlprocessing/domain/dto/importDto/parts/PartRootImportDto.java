package softuni.jsonprocessing.domain.dto.importDto.parts;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "parts")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartRootImportDto {

    @XmlElement(name = "part")
    private List<PartChildImportDto> parts;

    public PartRootImportDto() {
    }

    public List<PartChildImportDto> getParts() {
        return parts;
    }

    public void setParts(List<PartChildImportDto> parts) {
        this.parts = parts;
    }
}
