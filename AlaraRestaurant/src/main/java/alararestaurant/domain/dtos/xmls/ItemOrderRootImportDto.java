package alararestaurant.domain.dtos.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemOrderRootImportDto {

    @XmlElement(name = "item")
    private List<ItemOrderImportDto> orderItems;

    public ItemOrderRootImportDto() {
    }

    public List<ItemOrderImportDto> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ItemOrderImportDto> orderItems) {
        this.orderItems = orderItems;
    }
}
