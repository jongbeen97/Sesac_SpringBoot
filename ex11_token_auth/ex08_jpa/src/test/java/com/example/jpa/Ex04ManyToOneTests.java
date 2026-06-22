package com.example.jpa;



import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import com.example.jpa.ex04_many_to_one.Category;
import com.example.jpa.ex04_many_to_one.Item;
import com.example.jpa.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@SpringBootTest
class Ex04ManyToOneTests {

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
  @DisplayName("다대일 단방향 저장 및 조회 테스트")
  void ManyToOneTest() {
    //  저장(부모 엔티티를 먼저 영속화를 해야 함)
    Category electronics = new Category("electronics");
    // 영속화 
    em.persist(electronics); // 카테고리 저장 먼저하기 

    Item item = new Item("ipad", electronics);
    em.persist(item); // 영속성 컨텍스트 안에 집어 넣으라는 것이 엔티티는 두개 들어 있음. 

    // 관리 상태에서 없애버리기 
    em.flush(); // 쿼리를 DB로 날리는 것 (쓰기 지연 SQL 저장소의 쿼리를 DB로 날림)
    em.clear(); // 모든 managedEntity를 준영속상태로 변경 별도의 영역으로 빼 두겟다는 의미 

    //  조회(아이디를 이용한 조회) 
    // 조회를 하기 전 준영속 상태로 바꾼 이유는 영속상태로 두면 SELECT가 안된다. 관리 상태에서 1차 캐시에 데이터 날리지 않는다. 구경할수 없다면 학습을 할 수 없다. 우리가 그래서 준영속 상태로 바꾼 이유는 DB로 부터 SELECT를 하기 위해 준영속상태로 만든 것이다. 
    Item findItem = em.find(Item.class, item.getId());

    System.out.println("category class :"+findItem.getCategory().getClass().getName());

    System.out.println("category name: "+findItem.getCategory().getName());
     
  }
    

  
}