package hiberspring.domain.dtos.xmls.product;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;

@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductImportDto {


    @XmlAttribute
    private String name;
    @XmlAttribute
    private int clients;
    @XmlElement
    private String branch;

    public ProductImportDto() {
    }
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NotNull
    public int getClients() {
        return clients;
    }

    public void setClients(int clients) {
        this.clients = clients;
    }
    @NotNull
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
