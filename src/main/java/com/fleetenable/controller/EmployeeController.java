package com.fleetenable.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.fleetenable.models.Employee;
import com.fleetenable.models.TaxDetails;
import com.fleetenable.repositories.EmployeeRepository;

import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@PostMapping
	public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
		return ResponseEntity.ok(employeeRepository.save(employee));
	}

	@GetMapping("/{id}/tax")
	public ResponseEntity<TaxDetails> getEmployeeTaxDetails(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

		LocalDate startOfFinancialYear = LocalDate.of(LocalDate.now().getYear(), Month.APRIL, 1);
		if (employee.getDoj().isAfter(startOfFinancialYear)) {
			startOfFinancialYear = employee.getDoj();
		}

		long monthsWorked = ChronoUnit.MONTHS.between(startOfFinancialYear, LocalDate.now()) + 1;
		double totalSalary = employee.getSalary() * monthsWorked;
		double taxAmount = calculateTax(totalSalary);
		double cessAmount = totalSalary > 2500000 ? (totalSalary - 2500000) * 0.02 : 0;

		TaxDetails taxDetails = new TaxDetails(employee.getId(), employee.getFirstName(), employee.getLastName(),
				totalSalary, taxAmount, cessAmount);

		return ResponseEntity.ok(taxDetails);
	}

	private double calculateTax(double totalSalary) {
		if (totalSalary <= 250000) {
			return 0;
		} else if (totalSalary <= 500000) {
			return (totalSalary - 250000) * 0.05;
		} else if (totalSalary <= 1000000) {
			return 12500 + (totalSalary - 500000) * 0.10;
		} else {
			return 62500 + (totalSalary - 1000000) * 0.20;
		}
	}

}
