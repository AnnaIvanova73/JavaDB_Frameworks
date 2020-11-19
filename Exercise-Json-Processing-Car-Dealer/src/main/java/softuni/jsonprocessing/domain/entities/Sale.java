package softuni.jsonprocessing.domain.entities;


import javax.persistence.*;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity {

    private int discount;
    private Customer customer;
    private Car car;

    public Sale() {
    }


    @Column(name = "discount")
    public int getDiscount() {
        return discount;
    }
    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @ManyToOne(targetEntity = Customer.class)
    @JoinColumn(name = "customer_id",referencedColumnName = "id")
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @OneToOne
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
