package com.example.lab.domain.dtos;

public class CityDto {

    private long id;
    private String cityName;

    public CityDto() {
    }


    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
