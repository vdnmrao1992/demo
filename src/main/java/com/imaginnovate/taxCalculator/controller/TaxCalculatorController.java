package com.imaginnovate.taxCalculator.controller;

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

import com.imaginnovate.taxCalculator.model.Employee;
import com.imaginnovate.taxCalculator.model.EmployeeVo;
import com.imaginnovate.taxCalculator.service.EmployeeService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("v1")
public class TaxCalculatorController {

	@Autowired
	EmployeeService empServ;

	@RequestMapping(method = RequestMethod.POST, path = "/employeeDetails")
	public ResponseEntity<String> saveEmployee(@Valid @RequestBody Employee emp) {		
		int employeeId = empServ.employeeAdd(emp);
		System.out.println("New Employee created with employee Id : "+employeeId);
		return ResponseEntity.ok("Employee Created with ID :" + employeeId);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, path = "/taxApplicable")
	public ResponseEntity<List<EmployeeVo>> saveEmployee(@RequestParam("financialYear") @Pattern(regexp="\\d{4}") String financialYear) {
		List<EmployeeVo> employeeVoList = empServ.employeeDetails(financialYear);
		System.out.println("Response is : "+employeeVoList);
		return ResponseEntity.ok(employeeVoList);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
	    Map<String, Object> responseBody = new HashMap<>();
	    responseBody.put("error", "Validation failed");
	    responseBody.put("details", ex.getBindingResult().getAllErrors());
	    System.out.println(" Error Response is : "+responseBody);
	    return ResponseEntity.badRequest().body(responseBody);
	}


}
