package softuni.labmapping.domain.dtos.view;

public class EmployeeViewDto {
    private String firstName;
    private String lastName;
    private double salary;

    public EmployeeViewDto() {
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

    @Override
    public String toString() {
        String sb = String.format("   - %s %s %.2f%n", this.firstName,
                this.lastName, this.salary);
        return sb.trim();
    }
}
