package com.fleetenable.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fleetenable.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
