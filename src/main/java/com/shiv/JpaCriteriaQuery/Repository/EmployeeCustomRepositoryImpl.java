package com.shiv.JpaCriteriaQuery.Repository;

import com.shiv.JpaCriteriaQuery.entity.Employee;
import com.shiv.JpaCriteriaQuery.entity.Employee_;
import com.shiv.JpaCriteriaQuery.entity.SalaryOp;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Arrays;
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
    public List<Employee> GetEmployeesByorderBySalary(List<Employee> employee) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Employee.class);

        Root<Employee> rootEmp =  cq.from(Employee.class);

        Object[] firstnames = employee.stream().map(emp -> emp.getFirstname()).collect(Collectors.toList()).toArray();

        cq.select(rootEmp).where(rootEmp.get("firstname").in(firstnames)).orderBy(cb.asc(rootEmp.get("salary")));

        TypedQuery query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<Employee> GetMultiselectEmployees() {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Employee.class);

        Root<Employee> rootEmp = cq.from(Employee.class);

        cq.multiselect(rootEmp.get("id"),rootEmp.get("firstname")).where(cb.and(cb.ge(rootEmp.get("id"),1),(cb.le(rootEmp.get("id"),4)))).orderBy(cb.desc(rootEmp.get("id")));

        TypedQuery query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    @Override
    public List<Employee> GetEmployeesByfirstname(String fname) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Employee.class);

        Root<Employee> rootEmp = cq.from(Employee.class);

        cq.multiselect(rootEmp.get("id"),rootEmp.get("firstname")).where(cb.like(rootEmp.get("firstname"),fname+"%"));

        TypedQuery query = entityManager.createQuery(cq);

        return query.getResultList();
    }

    @Override
    public Long GetNumberofEmployees() {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Long.class);
        Root<Employee> rootEmp = cq.from(Employee.class);

        cq.multiselect(cb.count(rootEmp.get("id")));

        TypedQuery query = entityManager.createQuery(cq);

        return (Long) query.getSingleResult();
    }

    @Override
    public SalaryOp GetAllOperationsOnSalary() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(Object[].class);

        Root<Employee> rootEmp = cq.from(Employee.class);

        cq.multiselect(cb.count(rootEmp.get("id")),
                cb.max(rootEmp.get("salary")),
                cb.min(rootEmp.get("salary")),
                cb.avg(rootEmp.get("salary")),
                cb.sum(rootEmp.get("salary"))
                );

        TypedQuery query = entityManager.createQuery(cq);

        Object[] singleResult = (Object[]) query.getSingleResult();

        SalaryOp salaryOp = new SalaryOp();
        salaryOp.setCount("count is:-"+singleResult[0]);
        salaryOp.setMax( "Max is:-"+singleResult[1]);
        salaryOp.setMin("Min is:-"+singleResult[2]);
        salaryOp.setAvg("Avg is:-"+singleResult[3]);
        salaryOp.setSum("Sum is:-"+singleResult[4]);
        return salaryOp;
    }

    @Override
    public Integer UpdateEmployeeSalary(Long salary, String Firstname) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaUpdate cu = cb.createCriteriaUpdate(Employee.class);
        Root<Employee> root = cu.from(Employee.class);

        cu.set(root.get("salary"),salary).where(cb.equal(root.get("firstname"),Firstname));

        int count  = entityManager.createQuery(cu).executeUpdate();

        return count;
    }

    @Override
    public Integer DeleteEmployeeByName(String firstname) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaDelete cd = cb.createCriteriaDelete(Employee.class);
        Root<Employee> root = cd.from(Employee.class);

        cd.where(cb.like(root.get("firstname"), firstname+"%"));

        int count  = entityManager.createQuery(cd).executeUpdate();
        return count;
    }
}
