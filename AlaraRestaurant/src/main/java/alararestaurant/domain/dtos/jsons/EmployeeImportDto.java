package alararestaurant.domain.dtos.jsons;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

public class EmployeeImportDto {

    @Expose
    private String name;
    @Expose
    private int age;
    @Expose
    private String position;

    public EmployeeImportDto() {
    }


    @Length(min = 3,max = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Range(min = 15, max = 80)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Length(min = 3,max = 30)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
