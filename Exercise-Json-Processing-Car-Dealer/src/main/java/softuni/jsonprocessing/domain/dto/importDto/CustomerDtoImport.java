package softuni.jsonprocessing.domain.dto.importDto;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;

public class CustomerDtoImport {

    //    private String name;
    //    private LocalDateTime birthDate;
    //    private boolean isYoungDriver;
    //{"name":"Carole Witman", "birthDate":"1987-10-01T00:00:00", "isYoungDriver": false},

    @Expose
    private String name;
    @Expose
    private LocalDateTime birthDate;
    @Expose
    private boolean isYoungDriver;

    public CustomerDtoImport() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
