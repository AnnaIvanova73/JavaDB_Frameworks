package alararestaurant.domain.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    //customer – text (required)
    //
    //dateTime – date and time of the order (required)
    //
    //type – OrderType enumeration with possible values: “ForHere, ToGo (default: ForHere)” (required)
    //
    //employee – The employee who will process the order (required)
    //
    //orderItems – collection of type OrderItem

    private String customer;
    private LocalDateTime dateTime;
    private OrderType type;
    private Employee employee;
    private Set<OrderItem> orderItems;


    public Order() {
    }

    @Column(name = "customer",nullable = false,columnDefinition = "TEXT")
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Column(name = "date_time",nullable = false)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(name = "employee_id",nullable = false)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }


    @OneToMany(mappedBy = "order")
    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
