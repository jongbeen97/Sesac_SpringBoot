package com.example.request.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class UserRequestDTO {
  private String name; 
  private int age;

}
