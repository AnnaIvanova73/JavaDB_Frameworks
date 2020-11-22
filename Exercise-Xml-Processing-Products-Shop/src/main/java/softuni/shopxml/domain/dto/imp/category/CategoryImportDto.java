package softuni.shopxml.domain.dto.imp.category;

import org.hibernate.validator.constraints.Length;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryImportDto {
    @XmlElement
    private String name;

    public CategoryImportDto() {
    }
    @Length(min = 3, max = 15,message = "Name should be between 3 and 15 character's")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
