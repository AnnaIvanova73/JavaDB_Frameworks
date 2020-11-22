package softuni.jsonprocessing.domain.dto.export.query6;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleRootExportDtoQuery6 {

    @XmlElement(name = "sale")
    private List<SaleExportDtoQuery6> sales;

    public SaleRootExportDtoQuery6() {
    }

    public List<SaleExportDtoQuery6> getCars() {
        return sales;
    }

    public void setSales(List<SaleExportDtoQuery6> cars) {
        this.sales = cars;
    }
}
