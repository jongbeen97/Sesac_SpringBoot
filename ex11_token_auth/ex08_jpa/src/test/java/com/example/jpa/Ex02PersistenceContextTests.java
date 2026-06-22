package com.example.jpa;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.ex02_persistence_context.Book;
import com.example.jpa.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@SpringBootTest
class Ex02PersistenceContextTests {

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
  @DisplayName("1차 캐시 테스트")
  void identityAndCacheTest() {
    // 엔티티 생성
    Book book = new Book("Java의정석", "남궁성");
    // 엔티티 관리 시작 INSERT 날아가게 하면 안된다. 날아가지만, 관리 시작
    // 영속 상태가 되었다고 표현 1차 캐시에 저장된다
    em.persist(book);
    // 엔티티 조회 (find() 메서드 : 오직 ID를 이용해서만 조회
    // find메서드 기본 동작 : 1차 캐시 없으면 DB 조회

    Book findBook1 = em.find(Book.class, book.getId());
    Book findBook2 = em.find(Book.class, book.getId());

    // 주소 비교를 통해 동일한 엔티티인지 확인
    assertTrue(findBook1 == findBook2);
  }

  /*
  ==================두번째 테스트==========================
  */
 @Test
 @DisplayName("변경 감지(Dirty Check) 테스트")
 void DirtyCheckTest(){
  // Entity 생성 
  Book book = new Book("Java's the Complete Reference", "남궁성");
  // 영속 상태로 변경
  em.persist(book); 
  // DB 반영 후 준영속 상태로 전환 (관리 안되는 상태)
  em.flush(); // 쓰기 지연 SQL 저장소 에 있는 모든 쿼리문을 DB로 날리는 행위 
  em.clear(); // 준영속상태로 바꾼다 

  // DB로 부터 조회 ( 조회 결과는 영속 상태로 됨 )
  Book findBook = em.find(Book.class,book.getId());

  // 영속 상태의 엔티티 수정 (변경 상태에 의해 업데이트문 자동생성 -> 쓰기 지연 SQL 저장소에 보관)
  findBook.changeTitle("JavaIntermediate");

  // 트랜젝션 커밋(tx.Commit) 또는 저장소 비우기(em.flush())를 통해 DB로 쓰기 지연 SQL 저장소의 모든 쿼리를 날림 
  em.flush(); 
  
 }
}