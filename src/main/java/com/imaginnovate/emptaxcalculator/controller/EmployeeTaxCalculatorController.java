package com.imaginnovate.emptaxcalculator.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.imaginnovate.emptaxcalculator.model.Employee;
import com.imaginnovate.emptaxcalculator.model.EmployeeVo;
import com.imaginnovate.emptaxcalculator.service.EmployeeService;

@RestController
@RequestMapping("v1")
public class EmployeeTaxCalculatorController {

	@Autowired
	EmployeeService employee_Service;

	@RequestMapping(method = RequestMethod.POST, path = "/saveEmployee")
	public ResponseEntity<String> saveEmployee(@javax.validation.Valid @RequestBody Employee emp) {		
		int emp_Id = employee_Service.saveEmployee(emp);
		System.out.println("New Employee ID : "+emp_Id);
		return ResponseEntity.ok("New Employee ID :" + emp_Id);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, path = "/getTaxOfEmployee")
	public ResponseEntity<List<EmployeeVo>> getTaxOfEmployee(@RequestParam("financial_Year") @javax.validation.constraints.Pattern(regexp="\\d{4}") String financial_Year) {
		List<EmployeeVo> employeesList = employee_Service.getTaxOfEmployee(financial_Year);
		System.out.println("Tax of Employees Response is : "+employeesList);
		return ResponseEntity.ok(employeesList);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> validationExceptionHandler(MethodArgumentNotValidException ex) {
	    Map<String, Object> responseBody = new HashMap<>();
	    responseBody.put("error", "Validation failed");
	    responseBody.put("details", ex.getBindingResult().getAllErrors());
	    System.out.println(" Error Response is : "+responseBody);
	    return ResponseEntity.badRequest().body(responseBody);
	}


}
