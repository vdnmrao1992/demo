package com.imaginnovate.emptaxcalculator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imaginnovate.emptaxcalculator.model.Employee;
import com.imaginnovate.emptaxcalculator.model.EmployeeVo;
import com.imaginnovate.emptaxcalculator.repo.EmployeeRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepo empRepo;

	@Override
	public int saveEmployee(Employee emp) {

		return empRepo.save(emp).getId();
	}
	
	private double getTax(double salary) {
		double tax = 0.0;
		try {
		if (salary <= 250000) {
			System.out.println("no Tax - salary is less than 2,50,000/-");
            tax = 0.0;
        } else if (salary > 250000 && salary <= 500000) {
        	System.out.println(" salary is between 2,50,000/- and 5,00,000/-");
            tax = (salary - 250000) * 0.05;
            System.out.println(" calculated Tax is : "+tax);
        } else if (salary > 500000 && salary <= 100000) {
        	System.out.println(" salary is between 5,00,000/- and 1,00,000/-");
            tax = 12500 + (salary - 500000) * 0.1;
            System.out.println(" calculated Tax is : "+tax);
        } else if (salary > 1000000) {
        	 System.out.println(" salary is between 10,00,000/-");
        	 tax = (salary - 1000000) * 0.2 + 62500;
             System.out.println(" calculated Tax is : "+tax);
        }
		return tax;
		}catch(Exception e) {
			System.out.println("Exception in Tax method : "+ e.getMessage());
		}
		return tax;
	}
	
	private Double getCess(double income) {
		double cess = 0.0;
		try {
        System.out.println("Salary : "+income);  
		if (income > 2500000) {
			System.out.println("Cess Applied");
			cess = income*0.02;
			System.out.println("cess amount is : "+cess);	
		}
		return cess;
		}catch(Exception e) {
			System.out.println("Exception in Cess Method: "+ e.getMessage());
		}
		return cess;
	}

	@Override
	public List<EmployeeVo> getTaxOfEmployee(String from_Financial_Year) {

		List<EmployeeVo> employVoList = new ArrayList<EmployeeVo>();
        System.out.println(" From Financial Year : "+from_Financial_Year);
		String to_Financial_Year = String.valueOf(Integer.parseInt(from_Financial_Year) + 1);
		System.out.println(" To Financial Year : "+to_Financial_Year);
		Optional<List<Employee>> employee = empRepo.getEmployeeData(from_Financial_Year + "-04-01", to_Financial_Year + "-03-31");
		if (employee.isPresent()) {
			List<Employee> empList = employee.get();
			for (Employee emp : empList) {
				EmployeeVo employVo = new EmployeeVo();
				employVo.setFirstName(emp.getFirstName());
				employVo.setEmployeeCode(emp.getId());
				employVo.setLastName(emp.getLastName());
				employVo.setYearlySalary(emp.getSal() * 12);
				employVo.setTax(getTax(emp.getSal() * 12));
				employVo.setCess(getCess(emp.getSal() * 12));
				employVoList.add(employVo);
			}
           System.out.println(" Employee Tax Details : "+employVoList);
		}

		return employVoList;
	}

}
