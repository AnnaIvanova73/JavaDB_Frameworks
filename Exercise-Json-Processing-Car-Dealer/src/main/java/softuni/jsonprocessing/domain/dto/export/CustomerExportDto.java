package softuni.jsonprocessing.domain.dto.export;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class CustomerExportDto {

    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose

    private String birthDate;
    @Expose
    private boolean isYoungDriver;
    @Expose
    private Set<SalesExportDto> sales;

    public CustomerExportDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(boolean youngDriver) {
        isYoungDriver = youngDriver;
    }

    public Set<SalesExportDto> getSales() {
        return sales;
    }

    public void setSales(Set<SalesExportDto> sales) {
        this.sales = sales;
    }
}
