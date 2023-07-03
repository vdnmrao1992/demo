package com.imaginnovate.taxCalculator.service;

import java.util.List;

import com.imaginnovate.taxCalculator.model.Employee;
import com.imaginnovate.taxCalculator.model.EmployeeVo;

public interface EmployeeService {
	
	 public int employeeAdd(Employee emp);
	 
	 public List<EmployeeVo> employeeDetails(String financialYear);

}
