package alararestaurant.domain.entities;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{


    private String name;
    private int age;
    private Position position;
    private Set<Order> order;


    public Employee() {
    }


    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    @Range(min = 0, max = 100)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @ManyToOne
    @JoinColumn(name = "position_id",nullable = false)
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    @OneToMany(mappedBy = "employee")
    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }
}
