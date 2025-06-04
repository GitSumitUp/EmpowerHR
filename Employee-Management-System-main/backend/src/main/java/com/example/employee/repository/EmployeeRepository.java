package com.example.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.employee.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository</* Class Name */Employee, Integer/* Primary Key DataType */> {

  /*
   * Optional<Employee> findById(Integer employeeid);
   * 
   * Optional<Employee> findByAllId(Integer employeeid);
   * 
   * List<Employee> findByDepartmentDepartmentid(Department department);
   * 
   * Optional<Employee> findByFname(String fname);
   * 
   * Optional<Employee> findByLname(String lname);
   * 
   * Optional<Employee> findByAllLname(String lname);
   * 
   * Optional<Employee> findByAllActive(Boolean active);
   * 
   * Optional<Employee> findByDepartmentDepartmentid(Integer departmentid);
   * 
   * Optional<Employee> findByAllDepartmentDepartmentid(Integer departmentid);
   * 
   * @Query("select e from Employee e where e.employeeid=:employeeid")
   * Optional<Employee> findByCustomId(@Param("id") Integer id);
   * 
   * @Query("select e from Employee e where e.fname=:fname")
   * List<Employee> findByAllFname(@Param("fname") String fname);
   * 
   * @Query("select e from Employee e where e.department = :department")
   * Optional<Employee> findByDepartment(@Param("department") Department
   * department);
   * 
   */

  // JPQL -> Jakarta Persistence Query Language

  /*
   * The Jakarta Persistence Query Language is a platform-independent
   * object-oriented query language defined as part of the Jakarta Persistence
   * specification. JPQL is used to make queries against entities stored in a
   * relational database.
   * 
   * The Java Persistence Query Language (JPQL) is the query language defined by
   * JPA. JPQL is similar to SQL, but operates on objects, attributes and
   * relationships instead of tables and columns. JPQL can be used for reading (
   * SELECT ), as well as bulk updates ( UPDATE ) and deletes ( DELETE ).
   */

  // What is JPQL? How they used ?

  // Optional<Employee> findByEmployeeId(@Param("employeeid") Integer employeeid);
}

// custom methods, Queries