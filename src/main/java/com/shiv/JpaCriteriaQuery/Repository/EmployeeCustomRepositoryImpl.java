package com.shiv.JpaCriteriaQuery.Repository;

import com.shiv.JpaCriteriaQuery.entity.Employee;
import com.shiv.JpaCriteriaQuery.entity.Employee_;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeCustomRepositoryImpl implements EmployeeCustomRepository {

    @Autowired
    private EntityManager entityManager;


    @Override
    public List<Employee> findByFirstNameAndDepartment(String firstname, String department) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Employee.class);

        Root<Employee> employee =  cq.from(Employee.class);

        Predicate firstnamePredicate = cb.equal(employee.get(Employee_.FIRSTNAME), firstname);
        Predicate departmentPredicate = cb.equal(employee.get(Employee_.LASTNAME), department);

        cq.where(firstnamePredicate,departmentPredicate);

        TypedQuery query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Employee> orderBySalary(List<Employee> employee) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Employee.class);

        Root<Employee> rootEmp =  cq.from(Employee.class);

        Object[] firstnames = employee.stream().map(emp -> emp.getFirstname()).collect(Collectors.toList()).toArray();

        cq.select(rootEmp).where(rootEmp.get("firstname").in(firstnames)).orderBy(cb.asc(rootEmp.get("salary")));

        TypedQuery query = entityManager.createQuery(cq);
        return query.getResultList();
    }
}
