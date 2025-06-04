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
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.model.Department;
import com.example.employee.model.Employee;
import com.example.employee.repository.DepartmentRepository;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.PutMapping;

@RestController

@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:4200")

public class EmployeeController {

  @Autowired
  private EmployeeRepository repository;

  @Autowired
  private DepartmentRepository departmentRepository;

  // @PostMapping
  // public ResponseEntity<String> registerEmployee(@RequestBody Employee emp) {
  // System.out.println("Received employee: " + emp);

  // Employee employee = new Employee();
  // try {
  // if (emp.getDepartment() != null && emp.getDepartment().getDepartmentid() !=
  // 0) {
  // departmentRepository.findById(emp.getDepartment().getDepartmentid())
  // .ifPresent(employee::setDepartment);
  // }

  // employee.setFname(emp.getFname());
  // employee.setLname(emp.getLname());
  // employee.setPassword(emp.getPassword());
  // employee.setEmail(emp.getEmail());
  // employee.setDoj(emp.getDoj());
  // employee.setGender(emp.getGender());
  // employee.setActive(emp.getActive());

  // repository.save(employee);
  // return ResponseEntity.status(HttpStatus.CREATED).body("Employee registered
  // successfully!");
  // } catch (Exception e) {
  // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error
  // occurred while registering employee.");
  // }
  // }

  @PostMapping
  public Employee registerEmployee(@RequestBody Employee emp) {
    // System.out.println("Received employee: " + emp);
    Employee employee = new Employee();
    System.out.println("department: " + emp.getDepartment());

    if (emp.getDepartment() != null && emp.getDepartment().getDepartmentid() != null) {
      Integer departmentId = emp.getDepartment().getDepartmentid();
      Department dept = departmentRepository.findById(departmentId)
          .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));
      employee.setDepartment(dept);
    } else {
      employee.setDepartment(null); // Explicitly set to null if no department provided
    }

    employee.setFname(emp.getFname());
    employee.setLname(emp.getLname());
    employee.setPassword(emp.getPassword());
    employee.setEmail(emp.getEmail());
    employee.setDoj(emp.getDoj());
    employee.setGender(emp.getGender());
    employee.setActive(emp.getActive());

    Employee savedEmployee = repository.save(employee);

    System.out.println("Employee data Saved SuccessFully !!!!");
    return savedEmployee;
  }

  @GetMapping
  public List<Employee> getAllEmployees() {
    List<Employee> list = repository.findAll();
    return list;
  }

  @GetMapping("/getById/{id}")
  public Employee getEmployee(@PathVariable Integer id) {
    Employee employee = repository.findById(id).get();
    return employee;
  }

  // @PutMapping("/{id}")
  // public ResponseEntity<String> updateEmployee(@PathVariable Integer id,
  // @RequestBody Employee updatedData) {
  // try {
  // Employee employee = repository.findById(id).orElse(null);

  // if (employee == null) {
  // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found
  // with id: " + id);
  // }

  // if (updatedData.getDepartment() != null &&
  // updatedData.getDepartment().getDepartmentid() != 0) {
  // Department dept =
  // departmentRepository.findById(updatedData.getDepartment().getDepartmentid()).orElse(null);
  // if (dept == null) {
  // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid department
  // ID");
  // }
  // employee.setDepartment(dept);
  // } else {
  // employee.setDepartment(null);
  // }

  // employee.setFname(updatedData.getFname());
  // employee.setLname(updatedData.getLname());
  // employee.setEmail(updatedData.getEmail());
  // employee.setPassword(updatedData.getPassword());
  // employee.setDoj(updatedData.getDoj());
  // employee.setGender(updatedData.getGender());
  // employee.setActive(updatedData.getActive());

  // repository.save(employee);
  // return ResponseEntity.ok("Employee updated successfully!");
  // } catch (Exception e) {
  // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error
  // occurred while updating employee.");
  // }
  // }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateEmployee(@PathVariable Integer id, @RequestBody Employee updatedData) {

    try {

      Employee employee = repository.findById(id).orElse(null);

      if (employee == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found with id: " + id);
      }

      // Set department if provided
      if (updatedData.getDepartment() != null && updatedData.getDepartment().getDepartmentid() != 0) {
        Department dept = departmentRepository.findById(updatedData.getDepartment().getDepartmentid())
            .orElse(null);
        if (dept == null) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid department ID");
        }
        employee.setDepartment(dept);
      } else {
        employee.setDepartment(null); // This prevents errors if department is missing
      }

      // Update other fields
      employee.setFname(updatedData.getFname());
      employee.setLname(updatedData.getLname());
      employee.setEmail(updatedData.getEmail());
      employee.setPassword(updatedData.getPassword());
      employee.setDoj(updatedData.getDoj());
      employee.setGender(updatedData.getGender());
      employee.setActive(updatedData.getActive());

      repository.save(employee);
      return ResponseEntity.ok(employee);

    } catch (NullPointerException e) {
      return new ResponseEntity<>("Invalid department data",
          HttpStatus.BAD_REQUEST);
    } catch (RuntimeException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
  }

  // For Update value

  @DeleteMapping("/{id}")
  public void deleteEmployee(@PathVariable Integer id) {
    repository.findById(id).ifPresent(employee -> {
      employee.setActive(0);
      repository.save(employee);
    });
  }

}
