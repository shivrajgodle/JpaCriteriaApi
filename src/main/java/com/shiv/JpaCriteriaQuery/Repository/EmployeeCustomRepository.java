package com.shiv.JpaCriteriaQuery.Repository;

import com.shiv.JpaCriteriaQuery.entity.Employee;
import com.shiv.JpaCriteriaQuery.entity.SalaryOp;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeCustomRepository {

    public List<Employee> findByFirstNameAndDepartment(String firstname , String department);

    public List<Employee> GetEmployeesByorderBySalary(List<Employee> employee);

    public List<Employee> GetMultiselectEmployees();

    public List<Employee> GetEmployeesByfirstname(String fname);

    public Long GetNumberofEmployees();

    public SalaryOp GetAllOperationsOnSalary();

    @Transactional
    public Integer UpdateEmployeeSalary(Long salary , String Firstname);

    @Transactional
    public Integer DeleteEmployeeByName(String firstname);


}
