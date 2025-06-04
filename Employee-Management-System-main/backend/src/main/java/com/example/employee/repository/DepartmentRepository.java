package com.example.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.employee.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
  // Department findByDepartmentname(String departmentname);

  // Department findByMgrId(Integer id);

  /*
   * Optional: Represents a single value that might be present or absent. It's
   * about handling the possibility of a single result not existing.
   * 
   * List: Represents a collection of zero or more values. It's about handling
   * multiple results.
   */
}