package com.shiv.JpaCriteriaQuery.controller;

import com.shiv.JpaCriteriaQuery.Repository.EmployeeRepository;
import com.shiv.JpaCriteriaQuery.entity.Employee;
import com.shiv.JpaCriteriaQuery.specifications.EmployeeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
    public List<Employee> getEmployeesBysalary(@RequestBody List<Employee> employee){

        return employeeRepository.orderBySalary(employee);
    }


}
