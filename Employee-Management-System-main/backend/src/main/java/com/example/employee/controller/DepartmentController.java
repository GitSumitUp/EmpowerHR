package com.example.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.employee.model.Department;
import com.example.employee.model.Employee;
import com.example.employee.repository.DepartmentRepository;
import com.example.employee.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/department")
@CrossOrigin(origins = "http://localhost:4200")

public class DepartmentController {

  @Autowired
  private EmployeeRepository EmployeeRepository;

  @Autowired
  private DepartmentRepository repository;

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public ResponseEntity<?> createDepartment(@RequestBody Department dept) {
    System.out.println("Received department: " + dept);

    Department department = new Department();

    Integer empId = dept.getMgr().getId();

    Employee emp = EmployeeRepository.findById(empId)
        .orElseThrow(() -> new RuntimeException("Employee not found with id: " +
            empId));

    department.setMgr(emp);

    department.setDepartmentname(dept.getDepartmentname());

    Department savedDepartment = repository.save(department);

    return new ResponseEntity<Department>(savedDepartment, HttpStatus.CREATED);
  }

  @GetMapping
  public List<Department> getAllDepartments() {
    List<Department> list = repository.findAll();
    return list;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> removeDepartment(@PathVariable Integer id) {
    repository.deleteById(id);
    return ResponseEntity.ok("Deleted Successfully");
  }

}
