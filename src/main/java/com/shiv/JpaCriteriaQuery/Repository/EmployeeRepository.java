package com.shiv.JpaCriteriaQuery.Repository;

import com.shiv.JpaCriteriaQuery.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> , EmployeeCustomRepository , JpaSpecificationExecutor<Employee> {
}
