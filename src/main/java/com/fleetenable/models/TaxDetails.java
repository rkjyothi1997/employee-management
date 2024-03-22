package com.fleetenable.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaxDetails {
	
	private Long employeeId;
	private String firstName;
	private String lastName;
	private Double yearlySalary;
	private Double taxAmount;
	private Double cessAmount;

	public TaxDetails(Long employeeId, String firstName, String lastName, Double yearlySalary, Double taxAmount,
			Double cessAmount) {
		
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.yearlySalary = yearlySalary;
		this.taxAmount = taxAmount;
		this.cessAmount = cessAmount;
	}

}
