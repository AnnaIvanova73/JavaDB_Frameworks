package com.example.lab.services;

import com.example.lab.domain.dtos.seed.EmployeeSeedDto;
import com.example.lab.domain.dtos.view.EmployeeViewDto;

public interface EmployeeService {

  void save(EmployeeSeedDto employeeSeedDto);
  // подаваме DTO/през runner/, когато искаме да го save-нем или запазим


  //от база чисто ентити, подаваме чисто ентити връщаме дто
  //когато ще връщаме ентити, връщаме винаги дто му
  EmployeeViewDto finDByFirstAndLastName(String fn, String ln);
  // аз искам ти да връщаш employeeViewDto
}
