package softuni.shopxml.domain.dto.export.query1;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductRootExportDtoQuery1 {


    @XmlElement(name = "product")
    private List<ProductExportDtoQuery1> products;


    public ProductRootExportDtoQuery1() {
    }

    public List<ProductExportDtoQuery1> getProducts() {
        return products;
    }

    public void setProducts(List<ProductExportDtoQuery1> products) {
        this.products = products;
    }
}
