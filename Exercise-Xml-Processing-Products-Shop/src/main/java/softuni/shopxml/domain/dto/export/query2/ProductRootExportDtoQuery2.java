package softuni.shopxml.domain.dto.export.query2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductRootExportDtoQuery2 {

    @XmlElement(name = "product")
    private List<ProductExportDtoQuery2> soldProducts;

    public ProductRootExportDtoQuery2() {
    }

    public List<ProductExportDtoQuery2> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductExportDtoQuery2> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
