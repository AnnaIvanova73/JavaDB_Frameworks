package alararestaurant.domain.dtos.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "orders")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderRootImportDto {

    @XmlElement(name = "order")
    private List<OrderImportDto> orders;

    public OrderRootImportDto() {
    }

    public List<OrderImportDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderImportDto> orders) {
        this.orders = orders;
    }
}
