package com.example.lab.services.impl;

import com.example.lab.domain.dtos.CityDto;
import com.example.lab.domain.dtos.seed.EmployeeSeedDto;
import com.example.lab.domain.dtos.view.EmployeeViewDto;
import com.example.lab.domain.entities.City;
import com.example.lab.domain.entities.Employee;
import com.example.lab.domain.repositories.EmployeeRepository;
import com.example.lab.services.CityService;
import com.example.lab.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final ModelMapper modelMapper; // винаги
    // final защото искаме да имаме една единствена инстанция на даден обект
    private final EmployeeRepository employeeRepository;
    private final CityService cityService;
    //използваме репозитори, само когато метода трябва да ни хърли нещо просто,
  // пример findAll, във всеки друг случаи пишем нов сърви


    public EmployeeServiceImpl(ModelMapper modelMapper, EmployeeRepository employeeRepository, CityService cityService) {
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.cityService = cityService;
    }

    @Override
    public void save(EmployeeSeedDto employeeSeedDto) {
        Employee employee = this.modelMapper.map(employeeSeedDto,Employee.class);

        CityDto city = this.cityService.findByName(employeeSeedDto.getCityName());
        //в city dto за това специфично мапване тряба да има ид, защото не може да разпознае обекта от базат
        //transient error ако няма id
        //CityDto city има ид, защото обекта се връща от базата
        employee.setCity(this.modelMapper.map(city, City.class));
        this.employeeRepository.saveAndFlush(employee);

    }

    @Override
    public EmployeeViewDto finDByFirstAndLastName(String fn, String ln) {
        //Employee entity = this.employeeRepository.findByFirstNameAndLastName(fn, ln);
        EmployeeViewDto dto = this.modelMapper.
                map(this.employeeRepository.findByFirstNameAndLastName(fn, ln),EmployeeViewDto.class);

        return dto;
    }
}
