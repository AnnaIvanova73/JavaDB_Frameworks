package softuni.exam.domain.dtos.jsons;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class PlayerImportDto {
    //[
    //  {
    //    "firstName": "Kiril",
    //    "lastName": "Despodov",
    //    "number": 32,
    //    "salary": 150000.00,
    //    "position": "Invalid",
    //    "picture": {
    //      "url": "google.pictures#1"
    //    },
    //    "team": {
    //      "name": "West Valley",
    //      "picture": {
    //        "url": "fc_pictures_1"
    //      }
    //

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int number;
    @Expose
    private BigDecimal salary;
    @Expose
    private String position;
    @Expose
    private TeamJsonImportDto team;

    public PlayerImportDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Length(min = 3, max = 15, message = "Invalid player")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Min(value = 1, message = "Invalid player")
    @Max(value = 99, message = "Invalid player")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Min(value = 0, message = "Invalid player")
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public TeamJsonImportDto getTeam() {
        return team;
    }

    public void setTeam(TeamJsonImportDto team) {
        this.team = team;
    }
}
