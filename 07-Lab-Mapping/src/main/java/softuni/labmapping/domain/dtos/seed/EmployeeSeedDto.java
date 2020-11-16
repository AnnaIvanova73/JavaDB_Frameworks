package softuni.labmapping.domain.dtos.seed;

import java.time.LocalDate;

public class EmployeeSeedDto {

    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private double salary;
    private AddressSeedDto addressEmp;


    public EmployeeSeedDto() {
    }

    public EmployeeSeedDto(String firstName,
                           String lastName,
                           AddressSeedDto addressEmp,
                           double salary,
                           LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressEmp = addressEmp;
        this.salary = salary;
        this.birthday = birthday;
    }

    public EmployeeSeedDto(String firstName,
                           String lastName,
                           double salary,
                           LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


    public AddressSeedDto getAddressEmp() {
        return addressEmp;
    }

    public void setAddressEmp(AddressSeedDto addressEmp) {
        this.addressEmp = addressEmp;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
