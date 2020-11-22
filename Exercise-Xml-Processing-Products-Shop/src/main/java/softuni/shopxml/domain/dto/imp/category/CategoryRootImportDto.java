package softuni.shopxml.domain.dto.imp.category;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryRootImportDto {

    @XmlElement(name = "category")
    private List<CategoryImportDto> categories;


    public CategoryRootImportDto() {
    }

    public List<CategoryImportDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryImportDto> categories) {
        this.categories = categories;
    }
}
