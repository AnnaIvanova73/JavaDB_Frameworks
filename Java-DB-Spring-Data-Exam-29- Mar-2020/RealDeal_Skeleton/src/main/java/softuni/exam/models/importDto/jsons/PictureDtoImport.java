package softuni.exam.models.importDto.jsons;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public class PictureDtoImport {

    @Expose
    private String name;
    @Expose
    private LocalDateTime dateAndTime;
    @Expose
    private int car;

    public PictureDtoImport() {
    }

    @Length(min = 2,max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(LocalDateTime dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }
}

