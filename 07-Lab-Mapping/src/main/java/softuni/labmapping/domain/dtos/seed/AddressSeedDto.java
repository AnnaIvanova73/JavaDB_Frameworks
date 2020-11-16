package softuni.labmapping.domain.dtos.seed;

import java.util.Set;

public class AddressSeedDto {
    private String address;
    private Set<EmployeeSeedDto> employees;

    public AddressSeedDto() {
    }

    public AddressSeedDto(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<EmployeeSeedDto> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeSeedDto> employees) {
        this.employees = employees;
    }
}
