package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends Person{
    private String email;
    private BigDecimal salaryPerHours;
    private Set<Course> courses;
    public Teacher() {
    }


    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "salary_per_hour")
    public BigDecimal getSalaryPerHours() {
        return salaryPerHours;
    }

    public void setSalaryPerHours(BigDecimal salaryPerHours) {
        this.salaryPerHours = salaryPerHours;
    }

    @OneToMany(mappedBy = "teacher",targetEntity = Course.class)
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
