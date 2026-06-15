package com.example.jpa;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.ex03_embeddable.Address;
import com.example.jpa.ex03_embeddable.Company;
import com.example.jpa.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@SpringBootTest
class Ex03EmbeddableTest {

  // 엔티티 매니저 (영속성 컨텍스트 관리자)
  private EntityManager em;

  // 엔티티 트랜젝션
  private EntityTransaction tx;

  // 테스트 시작 전 엔티티 매니저를 만들기 위해 팩토리(공장)부터 지어둠
  @BeforeAll
  static void setUpBeforeClass() {
    JpaUtil.initFactory(); // 공장 초기화
  }

  // 각 테스트 시작 전 엔티티 매니저를 생성
  @BeforeEach
  void setUp() {
    em = JpaUtil.getEntityManager(); // 공장에서 만들어져 나옴 이때 트렌젝션 같이 넣어주자
    tx = em.getTransaction(); // JPA의 모든 데이터 변경은 트랜젝션 내부에서 실행되어야 함.
    tx.begin();
  }

  // 각 테스트 종료 후 엔티티 메니저를 닫아줌
  @AfterEach
  void tearDown() {
    // 트랜젝션도 닫아주어야 함.
    if (tx != null && tx.isActive()) {
      tx.rollback();
    }
    // 엔티티 메니저를 닫아줌
    if (em != null && em.isOpen()) {
      em.close();
    }
  }

  // 전체 테스트 종료 후 엔티티 매니저 팩토리를 닫아줌
  @AfterAll
  static void tearDownAfterClass() {
    JpaUtil.closeFactory();
  }

  // 이제부터 테스트 진행
  @Test
  @DisplayName("임베디드 타입 테스트")
  void EmbeddedTest() {
    Address office = new Address("Seoul", "mullae_daero", "98725");
    Address factory = new Address("Seoul", "digital-ro", "95112");

    Company company = new Company(1L,"새싹소프트",office,factory);
    em.persist(company);// INSERT는 만들어지지만 전송은 안된다. 컴퍼니 영속 상태로 변경 INSERT를 만드는 코드 
    em.flush(); // 쿼리 날리기 (INSERT 날아감) 날리는 코드
    Company findCompany = em.find(Company.class, 1L);
    assertEquals("새삭소프트", findCompany.getName());// 여젼히 엔티티는 관리 상태이다, 이유는 우리가 준영속으로 보내준 코드가 없기 때문이다. 
  }
    

  
}