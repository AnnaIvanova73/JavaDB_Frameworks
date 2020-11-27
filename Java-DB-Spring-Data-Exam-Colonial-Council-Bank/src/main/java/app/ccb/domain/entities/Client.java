package app.ccb.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client extends BaseEntity {

    private String fullName;
    private Integer age;
    private BankAccount bankAccount;
    private Set<Employee> employees;
    public Client() {
    }

    @Column(name = "full_name", nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    @Column
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account")
    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

   @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "employees_clients",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "employee")
    )
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
