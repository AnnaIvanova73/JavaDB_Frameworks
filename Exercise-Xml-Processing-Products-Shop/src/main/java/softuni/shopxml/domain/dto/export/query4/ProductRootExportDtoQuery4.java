package softuni.shopxml.domain.dto.export.query4;


import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductRootExportDtoQuery4 {

    @XmlElement(name = "product")
    private List<ProductExportDtoQuery4> soldProducts;
    @XmlAttribute
    private int count;

    public ProductRootExportDtoQuery4() {
    }

    public List<ProductExportDtoQuery4> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductExportDtoQuery4> soldProducts) {
        this.soldProducts = soldProducts;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
