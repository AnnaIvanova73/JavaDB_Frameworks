package com.example.lab.services.impl;

import com.example.lab.domain.dtos.CityDto;
import com.example.lab.domain.dtos.seed.CitySeedDto;
import com.example.lab.domain.dtos.view.CityViewDto;
import com.example.lab.domain.dtos.view.EmployeeViewDto;
import com.example.lab.domain.entities.City;
import com.example.lab.domain.repositories.CityRepository;
import com.example.lab.services.CityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public CityServiceImpl(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(CitySeedDto citySeedDto) {
        this.cityRepository.save(this.modelMapper.map(citySeedDto, City.class
        ));
    }

    @Override
    public CityDto findByName(String name) {

        return this.modelMapper.map(this.cityRepository.findByName(name),CityDto.class);
    }

    @Override
    public CityViewDto getByName(String name) {
        City city = this.cityRepository.findByName(name);
        CityViewDto cityViewDto = this.modelMapper.map(city, CityViewDto.class);

        cityViewDto.setEmployeeViewDtoSet(city.getEmployees()
                .stream()
                .map(e -> this.modelMapper.map(e, EmployeeViewDto.class))
                .collect(Collectors.toSet()));
        // пишем сървиз, които поема generic , полувачаш някакъв сет generic върни ми друг сет generic
        return cityViewDto;
    }
}
