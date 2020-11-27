package alararestaurant.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "positions")
public class Position extends BaseEntity {

    private String name;
    private Set<Employee> employees;

    public Position() {
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "position",fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
