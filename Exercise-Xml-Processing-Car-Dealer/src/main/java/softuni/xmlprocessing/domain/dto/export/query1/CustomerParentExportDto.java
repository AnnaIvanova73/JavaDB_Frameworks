package softuni.jsonprocessing.domain.dto.export.query1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerParentExportDto {

    @XmlElement(name = "customer")
    private List<CustomerChildExportDto> customers;

    public CustomerParentExportDto() {
    }

    public List<CustomerChildExportDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerChildExportDto> customers) {
        this.customers = customers;
    }
}
