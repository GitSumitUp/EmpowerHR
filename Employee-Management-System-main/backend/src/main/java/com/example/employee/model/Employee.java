package com.example.employee.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Integer id;

  private String fname;
  private String lname;
  private String password;
  private String email;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "department", referencedColumnName = "departmentid")
  private Department department;

  private LocalDate doj;
  private String gender;
  private int active;
}
