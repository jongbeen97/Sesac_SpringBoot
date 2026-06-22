package com.example.jpa.ex03_embeddable;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "companies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Company {

  @Id
  private Long id;

  private String name;

  // 이거는 칼럼이 아니다. 이름을 막 Office _ 이런거 하지 말아라
  // 여기 뭐가 들어와야 할까? City, Street zipCode가 들어와야 함
  @Embedded // 복합 타입을 부르는 것 (임베디드 타입 포함) (Address의 City,Street,zipCode 칼럼이 생긴다. )

  @AttributeOverrides({
      @AttributeOverride(name = "city", column = @Column(name = "office_city")),
      @AttributeOverride(name = "street", column = @Column(name = "office_street")),
      @AttributeOverride(name = "zipcode", column = @Column(name = "office_zip_code"))
  })
  private Address officeAddress;

  @Embedded // 임베디드 타입 포함 (칼럼이름 충돌 발생할 것 -> 칼럼 이름 제정의 )
  @AttributeOverrides({
      @AttributeOverride(name = "city", column = @Column(name = "factory_city")),
      @AttributeOverride(name = "street", column = @Column(name = "factory_street")),
      @AttributeOverride(name = "zipcode", column = @Column(name = "factory_zip_code"))
  })
  private Address factoryAddress;

}
