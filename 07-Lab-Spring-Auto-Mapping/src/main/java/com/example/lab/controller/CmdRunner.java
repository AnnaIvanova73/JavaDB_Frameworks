package com.example.lab.controller;

import com.example.lab.domain.dtos.UserLoginDto;
import com.example.lab.domain.dtos.seed.UserSeedDto;
import com.example.lab.services.CityService;
import com.example.lab.services.EmployeeService;
import com.example.lab.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CmdRunner implements CommandLineRunner {

    private final EmployeeService employeeService;
    private final CityService cityService;
    private final UserService userService;
    public CmdRunner(EmployeeService employeeService, CityService cityService, UserService userService) {
        this.employeeService = employeeService;
        this.cityService = cityService;
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception {

        UserSeedDto userSeedDto = new UserSeedDto("Joro","Iliev",19,"joro@abv.bg");
        this.userService.save(userSeedDto);


        UserLoginDto loginDto = new UserLoginDto("Joro","Iliev");
        boolean isLogged = this.userService.login(loginDto);

        if(isLogged){
            System.out.printf("%s is logged successfully !%n",loginDto.getUsername());
        }else{
            System.out.printf("No such user %s%n",loginDto.getUsername());
        }


// -----------------------------------------------------------------------------------
     /*   EmployeeSeedDto employeeSeedDto = new EmployeeSeedDto("Ivan","Ivanov",15000,
                "Sofia");


        CitySeedDto cityDto1 = new CitySeedDto("Sofia");
        CitySeedDto cityDto2 = new CitySeedDto("Plovdiv");
        CitySeedDto cityDto3 = new CitySeedDto("Burgas");


        this.cityService.save(cityDto1);
        this.cityService.save(cityDto2);
        this.cityService.save(cityDto3);


    this.employeeService.save(employeeSeedDto); */

//        EmployeeViewDto employeeViewDto =
//                this.employeeService.finDByFirstAndLastName("Ivan","Ivanov");
        //взима коректно името на града, защото имаме клас с име City, който има поле Name,
        //a EmployeeViewDto има поле String cityName;

        /*EmployeeSeedDto employeeSeedDto1 = new EmployeeSeedDto("Ivan", "Ivanova", 2800, "Sofia");
        EmployeeSeedDto employeeSeedDto2 = new EmployeeSeedDto("Pesho", "Stamt", 1600, "Sofia");
        EmployeeSeedDto employeeSeedDto3 = new EmployeeSeedDto("Gosho", "Pesheb", 500, "Sofia");

        this.employeeService.save(employeeSeedDto1);
        this.employeeService.save(employeeSeedDto2);
        this.employeeService.save(employeeSeedDto3);

        System.out.println();*/

//        CityViewDto cityViewDto= this.cityService.getByName("Sofia");
//        System.out.println();

//
//         EmployeeViewDto employeeViewDto = this.employeeService.finDByFirstAndLastName("Ivan","Ivanov");


    }
}
