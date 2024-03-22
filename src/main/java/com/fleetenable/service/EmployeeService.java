package com.fleetenable.service;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import com.fleetenable.models.Employee;
import com.fleetenable.models.TaxDetails;
import com.fleetenable.utils.EmployeeUtil;

public class EmployeeService {

	public TaxDetails getTaxDetails(Employee employee) {

		LocalDate startOfFinancialYear = LocalDate.of(LocalDate.now().getYear(), Month.APRIL, 1);
		if (employee.getDoj().isAfter(startOfFinancialYear)) {
			startOfFinancialYear = employee.getDoj();
		}

		long monthsWorked = ChronoUnit.MONTHS.between(startOfFinancialYear.withDayOfMonth(1), LocalDate.now()) + 1;
		double totalSalary = employee.getSalary() * monthsWorked;

		if (employee.getDoj().getDayOfMonth() > 1) {
			double dailyRate = employee.getSalary() / 30;
			int daysToDeduct = employee.getDoj().getDayOfMonth() - 1;
			totalSalary -= dailyRate * daysToDeduct;
		}

		double taxAmount = EmployeeUtil.calculateTax(totalSalary);
		double cessAmount = totalSalary > 2500000 ? (totalSalary - 2500000) * 0.02 : 0;

		TaxDetails taxDetails = new TaxDetails(employee.getId(), employee.getFirstName(), employee.getLastName(),
				totalSalary, taxAmount, cessAmount);

		return taxDetails;
	}

}
