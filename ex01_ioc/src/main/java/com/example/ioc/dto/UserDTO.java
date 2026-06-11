package com.example.ioc.dto;

public class UserDTO {
  private String name;
  private int age;

  public UserDTO() {

  }

  // constructor
  public UserDTO(String name, int age) {
    this.name = name;
    this.age = age;
  }

  // Getter Setter
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  // ToString
  @Override
  public String toString() {
    return "UserDTO [name=" + name + ", age=" + age + "]";
  }
}
