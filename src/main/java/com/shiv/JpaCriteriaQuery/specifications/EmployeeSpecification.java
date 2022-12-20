package com.shiv.JpaCriteriaQuery.specifications;

import com.shiv.JpaCriteriaQuery.entity.Employee;
import com.shiv.JpaCriteriaQuery.entity.Employee_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class EmployeeSpecification {

    public static Specification<Employee> hasFirstName(String firstName){
        return ((root, query, criteriaBuilder) -> {
           return criteriaBuilder.equal(root.get(Employee_.FIRSTNAME),firstName);
        });
    }

    public static Specification<Employee> containsLastName(String lastname){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get(Employee_.LASTNAME),"%"+lastname+"%");
        });
    }

    public static Specification<Employee> hasDepartment(String department){
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(Employee_.DEPARTMENT),department);
        });
    }

}
