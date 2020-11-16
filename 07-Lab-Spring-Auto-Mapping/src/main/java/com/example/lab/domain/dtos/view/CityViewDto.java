package com.example.lab.domain.dtos.view;

import java.util.Set;



public class CityViewDto {

    private String name;
    private Set<EmployeeViewDto> employeeViewDtoSet;


    public CityViewDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeViewDto> getEmployeeViewDtoSet() {
        return employeeViewDtoSet;
    }

    public void setEmployeeViewDtoSet(Set<EmployeeViewDto> employeeViewDtoSet) {
        this.employeeViewDtoSet = employeeViewDtoSet;
    }
}
