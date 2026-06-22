package com.example.jpa.ex03_embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 임베디드 타입 : 복합타입 ( 여러 타입을 하나의 객체로 묶어 관리 )
@Embeddable 

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {

  private String city;
  private String street;
  @Column(name = "zip_code")
  private String zipcode;

  public Address(String city, String street, String zipcode) {
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }



}
