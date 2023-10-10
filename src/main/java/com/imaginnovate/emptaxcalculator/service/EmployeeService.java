package com.imaginnovate.emptaxcalculator.service;

import java.util.List;

import com.imaginnovate.emptaxcalculator.model.Employee;
import com.imaginnovate.emptaxcalculator.model.EmployeeVo;

public interface EmployeeService {
	
	 public int saveEmployee(Employee emp);
	 
	 public List<EmployeeVo> getTaxOfEmployee(String financialYear);

}
