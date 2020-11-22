package softuni.jsonprocessing.domain.dto.importDto.customers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.*;
import java.util.List;


@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerRootImportDto {

    @XmlElement(name = "customer")
    private List<CustomerChildImportDto> customers;


    public CustomerRootImportDto() {
    }

    public List<CustomerChildImportDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerChildImportDto> customers) {
        this.customers = customers;
    }
}
