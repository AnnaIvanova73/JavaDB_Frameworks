package softuni.shopxml.domain.dto.export.query3;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryExportDtoQuery3 {

    @XmlAttribute
    private String name;
    @XmlElement(name = "products-count")
    private int productsCounts;
    @XmlElement(name = "average-price")
    private BigDecimal avgPrice;
    @XmlElement(name = "total-revenue")
    private BigDecimal totalRevenue;

    public CategoryExportDtoQuery3() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductsCounts() {
        return productsCounts;
    }

    public void setProductsCounts(int productsCounts) {
        this.productsCounts = productsCounts;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
