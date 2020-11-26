package hiberspring.domain.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "employee_card")
public class EmployeeCard extends BaseEntity{

    private String number;
    private Employee employee;

    public EmployeeCard() {
    }

    @Column(unique = true,nullable = false)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @OneToOne(mappedBy = "card")
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
