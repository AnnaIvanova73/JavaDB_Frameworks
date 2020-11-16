package softuni.labmapping.domain.dtos.view;

import java.util.Set;

public class ManagerView {

    private Long id;
    private String firstName;
    private String lastName;
    private Set<EmployeeViewDto> employees;

    public ManagerView() {
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

    public Set<EmployeeViewDto> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeViewDto> employees) {
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
       sb.append(String.format("%s %s | Employees: %d%n",
               this.firstName,this.lastName,this.employees.size()));

       this.employees.forEach(e -> sb.append(e).append(System.lineSeparator()));
        return sb.toString().trim();
    }
}
