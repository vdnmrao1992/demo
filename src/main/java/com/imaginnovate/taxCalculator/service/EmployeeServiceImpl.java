package com.imaginnovate.taxCalculator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imaginnovate.taxCalculator.model.Employee;
import com.imaginnovate.taxCalculator.model.EmployeeVo;
import com.imaginnovate.taxCalculator.repo.EmployeeRepo;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepo repo;

	@Override
	public int employeeAdd(Employee emp) {

		return repo.save(emp).getId();
	}
	
	private Double applicableCess(double income) {
		double cess = 0.0;
		try {
        System.out.println("Input salary is : "+income);  
		if (income > 2500000) {
			cess = income*0.02;
			System.out.println("Income is greater than 25,00,000/-");
			System.out.println("cess amount applicable is : "+cess);
			
		}
		return cess;
		}catch(Exception e) {
			System.out.println("Exception in applicableCess method : "+ e.getMessage());
		}
		return cess;
	}
	
	private double applicableTax(double income) {
		double tax = 0.0;
		try {
		if (income <= 250000) {
			System.out.println("Income is less than 2,50,000/- So no Tax");
            tax = 0.0;
        } else if (income > 250000 && income <= 500000) {
            tax = (income - 250000) * 0.05;
            System.out.println(" Income is between 2,50,000/- and 5,00,000/-");
            System.out.println(" calculated Tax is : "+tax);
        } else if (income > 500000 && income <= 100000) {        	
            tax = 12500 + (income - 500000) * 0.1;
            System.out.println(" Income is between 5,00,000/- and 1,00,000/-");
            System.out.println(" calculated Tax is : "+tax);
        } else if (income > 1000000) {
        	 tax = (income - 1000000) * 0.2 + 62500;
        	 System.out.println(" Income is between 10,00,000/-");
             System.out.println(" calculated Tax is : "+tax);
        }
		return tax;
		}catch(Exception e) {
			System.out.println("Exception in applicableTax method : "+ e.getMessage());
		}
		return tax;
	}

	@Override
	public List<EmployeeVo> employeeDetails(String frFrom) {

		List<EmployeeVo> employVoList = new ArrayList<EmployeeVo>();
        System.out.println(" Input From Date is : "+frFrom);
		String frTo = String.valueOf(Integer.parseInt(frFrom) + 1);
		System.out.println(" Input To Date is : "+frTo);
		Optional<List<Employee>> employee = repo.getEmployeeData(frFrom + "-04-01", frTo + "-03-31");
		if (employee.isPresent()) {
			List<Employee> empList = employee.get();
			for (Employee emp : empList) {
				EmployeeVo employVo = new EmployeeVo();
				employVo.setFirstName(emp.getFirstName());
				employVo.setEmployeeCode(emp.getId());
				employVo.setLastName(emp.getLastName());
				employVo.setYearlSalary(emp.getSal() * 12);
				employVo.setTax(applicableTax(emp.getSal() * 12));
				employVo.setCess(applicableCess(emp.getSal() * 12));
				employVoList.add(employVo);
			}
           System.out.println(" response is : "+employVoList);
		}

		return employVoList;
	}

}
