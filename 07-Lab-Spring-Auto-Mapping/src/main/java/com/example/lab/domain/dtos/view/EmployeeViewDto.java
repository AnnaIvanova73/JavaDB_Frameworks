package com.example.lab.domain.dtos.view;

import java.math.BigDecimal;

public class EmployeeViewDto {

    private String firstName;

    private BigDecimal salary;

    private String cityName;



    public EmployeeViewDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
