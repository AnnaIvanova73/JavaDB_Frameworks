package softuni.shopxml.domain.dto.export.query2;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserExportDtoQuery {

    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlElement(name = "sold-products")
    ProductRootExportDtoQuery2 soldProducts;

    public UserExportDtoQuery() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ProductRootExportDtoQuery2 getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductRootExportDtoQuery2 soldProducts) {
        this.soldProducts = soldProducts;
    }



}
