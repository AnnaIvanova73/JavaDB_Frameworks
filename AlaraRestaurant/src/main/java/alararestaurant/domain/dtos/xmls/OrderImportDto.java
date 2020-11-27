package alararestaurant.domain.dtos.xmls;

import alararestaurant.config.LocalDateTimeAdapter;
import alararestaurant.domain.entities.OrderType;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlRootElement(name = "order")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderImportDto {

    //<customer>Garry</customer>
    //
    //    <employee>Maxwell Shanahan</employee>
    //
    //    <date-time>21/08/2017 13:22</date-time>
    //
    //    <type>ForHere</type>

    @XmlElement
    private String customer;
    @XmlElement(name = "employee")
    private String employeeName;
    @XmlElement(name = "date-time")
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime dateTime;
    @XmlElement(name = "type")
    private OrderType typeName;

    @XmlElement(name = "items")
    private ItemOrderRootImportDto orderItems;

    public OrderImportDto() {
    }

    @NotNull
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @NotNull
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    //@XmlEnum
    public OrderType getTypeName() {
        return typeName;
    }

    public void setTypeName(OrderType typeName) {
        this.typeName = typeName;
    }

    public ItemOrderRootImportDto getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ItemOrderRootImportDto orderItems) {
        this.orderItems = orderItems;
    }
}
