package com.shiv.JpaCriteriaQuery.controller;

import com.shiv.JpaCriteriaQuery.Repository.EmployeeRepository;
import com.shiv.JpaCriteriaQuery.entity.Employee;
import com.shiv.JpaCriteriaQuery.entity.SalaryOp;
import com.shiv.JpaCriteriaQuery.specifications.EmployeeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    @GetMapping("/employees")
    public List<Employee> findAllEmployees(){
        return employeeRepository.findAll();
    }

    //by common way
//    @GetMapping("/employees/{firstname}/{department}")
//    public List<Employee> findByFirstnameAndDepartment(@PathVariable("firstname") String firstname , @PathVariable("department") String department){
//
//        return employeeRepository.findByFirstNameAndDepartment(firstname, department);
//    }

    //by using specification

    @GetMapping("/employees/{firstname}/{department}")
    public List<Employee> findByFirstnameAndDepartment(@PathVariable("firstname") String firstname , @PathVariable("department") String department){

        Specification<Employee> specification = Specification.where(EmployeeSpecification.hasFirstName(firstname).and(EmployeeSpecification.hasDepartment(department)));

        return employeeRepository.findAll(specification);
    }

    @GetMapping("/employees/{lastname}")
    public List<Employee> findByLastname(@PathVariable("lastname") String lastname){

        Specification<Employee> specification = Specification.where(EmployeeSpecification.containsLastName(lastname));

        return employeeRepository.findAll(specification);
    }


    //orderby
    @GetMapping("/employees/orderby")
    public List<Employee> getEmployeesByorderBySalary(@RequestBody List<Employee> employee){

        return employeeRepository.GetEmployeesByorderBySalary(employee);
    }



    //multiselect id and name from 1 to 4
    @GetMapping("/employees/multiselect")
    public List<Employee> getMultiselectEmployees(){
        return employeeRepository.GetMultiselectEmployees();
    }

    //get employee by first name

    @GetMapping("/employee/{firstname}")
    public List<Employee> getEmployeesByfirstname(@PathVariable("firstname") String firstname){

        return employeeRepository.GetEmployeesByfirstname(firstname);
    }

    //get number of employees

    @GetMapping("/employee/count")
    public Long getNumberofEmployees(){
        return employeeRepository.GetNumberofEmployees();
    }

    //get all operation on salary like min , max , avg , sum and count
    @GetMapping("/employee/alloperations")
    public ResponseEntity<SalaryOp> getAllOperationsOnSalary(){
        SalaryOp salaryOp = employeeRepository.GetAllOperationsOnSalary();

       return new ResponseEntity<SalaryOp>(salaryOp, HttpStatus.OK);
    }

    //update salary of employee UpdateEmployeeSalary

    @PutMapping("/employee/{salary}/{firstname}")
    public ResponseEntity<Integer> getAllOperationsOnSalary(@PathVariable("salary") Long salary , @PathVariable("firstname") String firstname){
        Integer integer = employeeRepository.UpdateEmployeeSalary(salary , firstname);
        return new ResponseEntity<Integer>(integer, HttpStatus.OK);
    }

    //delete employee by name

    @DeleteMapping("/employees/delete/{name}")
    public ResponseEntity<Integer> deleteEmployeeByName(@PathVariable("name") String name){
        Integer integer = employeeRepository.DeleteEmployeeByName(name);
        return new ResponseEntity<Integer>(integer, HttpStatus.OK);
    }

}
