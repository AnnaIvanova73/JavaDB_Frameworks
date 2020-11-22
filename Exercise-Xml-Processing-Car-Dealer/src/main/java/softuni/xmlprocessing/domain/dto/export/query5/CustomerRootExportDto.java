package softuni.jsonprocessing.domain.dto.export.query5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerRootExportDto {

    @XmlElement(name = "customer")
    private List<CustomerExportDto> customers;


    public CustomerRootExportDto() {
    }

    public List<CustomerExportDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerExportDto> customers) {
        this.customers = customers;
    }
}
