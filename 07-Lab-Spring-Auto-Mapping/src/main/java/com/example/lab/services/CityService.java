package com.example.lab.services;

import com.example.lab.domain.dtos.CityDto;
import com.example.lab.domain.dtos.seed.CitySeedDto;
import com.example.lab.domain.dtos.view.CityViewDto;

public interface CityService {

    void save(CitySeedDto citySeedDto);

    CityDto findByName(String name);

    CityViewDto getByName (String name);
}
