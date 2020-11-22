package softuni.jsonprocessing.domain.dto.importDto.suppliers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierRootImportDto {


    @XmlElement(name = "supplier") // същия като името на child класа, името на елемента, защото рута има елементи,
    // попитай се какво има вътре?
    private List<SupplierChildImportDto> suppliers;

    public SupplierRootImportDto() {
    }

    public List<SupplierChildImportDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierChildImportDto> suppliers) {
        this.suppliers = suppliers;
    }
}
