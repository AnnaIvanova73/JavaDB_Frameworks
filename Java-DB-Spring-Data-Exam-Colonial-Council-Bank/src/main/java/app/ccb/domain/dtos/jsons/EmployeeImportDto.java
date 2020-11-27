package app.ccb.domain.dtos.jsons;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeImportDto {

    @Expose
    @SerializedName("full_name")
    private String fullName;
    @Expose
    private BigDecimal salary;
    @Expose
    @SerializedName("started_on")
    private LocalDate startedOn;
    @Expose
    @SerializedName("branch_name")
    private String branch;

    public EmployeeImportDto() {
    }

    @NotNull
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(LocalDate startedOn) {
        this.startedOn = startedOn;
    }
    @NotNull
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
