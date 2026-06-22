package com.example.jpa.ex07_jpql;
// 부모 엔티티 

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "department")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String deptname;

  @OneToMany(mappedBy = "department")
  private List<Employee> employees = new ArrayList<>();

  public Department(String deptname) {
    this.deptname = deptname;
  }

}
