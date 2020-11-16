package softuni.labmapping.domain.entities;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity  {
    private String address;
    private Set<Employee> employees;

    public Address() {
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @OneToMany(mappedBy = "addressEmp")
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
