package softuni.jsonprocessing.domain.dto.export.query3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierRootExportDto {

    @XmlElement(name = "supplier")
    private List<SupplierChildExportDto> suppliers;

    public SupplierRootExportDto() {
    }

    public List<SupplierChildExportDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierChildExportDto> suppliers) {
        this.suppliers = suppliers;
    }
}
