package com.example.jpa.ex07_jpql;
// 자식 엔티티 - 양방향 연관관계 맺어보는 것 

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "employees")
@NoArgsConstructor
@Getter
@ToString
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Integer salary;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dept_id")
  private Department department;

  public Employee(String name, Integer salary) {
    this.name = name;
    this.salary = salary;
  }


public void setDePartment(Department department){
  this.department = department; // 디파트먼트 받아와서 받아온 디파트 먼트를 setter처럼 제작을 함. 이를 불러서 디파트 받아오면 그게 내 부서가 되는 것이다. 
}


}
