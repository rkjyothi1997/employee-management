package com.fleetenable.utils;

public class EmployeeUtil {

	public static double calculateTax(double totalSalary) {
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
