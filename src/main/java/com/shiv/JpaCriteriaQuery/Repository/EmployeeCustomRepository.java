package com.shiv.JpaCriteriaQuery.Repository;

import com.shiv.JpaCriteriaQuery.entity.Employee;

import java.util.List;

public interface EmployeeCustomRepository {

    public List<Employee> findByFirstNameAndDepartment(String firstname , String department);
}
