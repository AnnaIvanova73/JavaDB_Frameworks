package com.example.lab.domain.dtos.seed;

public class EmployeeSeedDto {

    private String firstName;
    private String lastName;
    private double salary;
    private String cityName;

    public EmployeeSeedDto() {
    }

    public EmployeeSeedDto(String firstName, String lastName, double salary, String addressCity) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.cityName = addressCity;
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
        return this.salary;
    }

    public void setSalary (double salary) {
        this.salary = salary;
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
