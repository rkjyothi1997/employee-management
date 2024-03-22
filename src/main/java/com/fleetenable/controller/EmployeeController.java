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
import com.fleetenable.service.EmployeeService;

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

	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
		return ResponseEntity.ok(employeeRepository.save(employee));
	}

	@GetMapping("/{id}/tax")
	public ResponseEntity<TaxDetails> getEmployeeTaxDetails(@PathVariable Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

		TaxDetails taxDetails = employeeService.getTaxDetailsWithRules(employee);

		return ResponseEntity.ok(taxDetails);
	}

}
