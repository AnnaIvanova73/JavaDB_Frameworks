package softuni.shopxml.domain.dto.export.query3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryRootExportDtoQuery3 {

    @XmlElement(name = "category")
    private List<CategoryExportDtoQuery3> categories;


    public CategoryRootExportDtoQuery3() {
    }

    public List<CategoryExportDtoQuery3> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryExportDtoQuery3> categories) {
        this.categories = categories;
    }
}
