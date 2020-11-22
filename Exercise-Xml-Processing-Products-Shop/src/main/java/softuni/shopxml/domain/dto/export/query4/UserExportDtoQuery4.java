package softuni.shopxml.domain.dto.export.query4;


import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserExportDtoQuery4 {

    @XmlAttribute(name = "first-name")
    private String firstName;
    @XmlAttribute(name = "last-name")
    private String lastName;
    @XmlAttribute
    private int age;

    @XmlElement(name = "sold-products")
    private ProductRootExportDtoQuery4 soldProducts;

    public UserExportDtoQuery4() {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ProductRootExportDtoQuery4 getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductRootExportDtoQuery4 soldProducts) {
        this.soldProducts = soldProducts;
    }
}
