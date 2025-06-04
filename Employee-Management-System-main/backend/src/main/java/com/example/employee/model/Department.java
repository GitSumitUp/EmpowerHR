
package com.example.employee.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor

public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Integer departmentid;
  private String departmentname;

  // Manager is an Employee — Foreign Key to employee.id
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "mgr", referencedColumnName = "id")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Employee mgr;
}