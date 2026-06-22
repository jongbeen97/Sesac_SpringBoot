package com.example.jpa;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.ex07_jpql.Department;
import com.example.jpa.ex07_jpql.Employee;
import com.example.jpa.util.JpaUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@SpringBootTest
class Ex07JpqlTests {

  // 엔티티 매니저 (영속성 컨텍스트 관리자)
  private EntityManager em;

  // 엔티티 트랜잭션
  private EntityTransaction tx;

  // 테스트 시작 전 엔티티 매니저를 만들기 위해 팩토리(공장)부터 지어둠
  @BeforeAll
  static void setUpBeforeClass() {
    JpaUtil.initFactory();
  }

  // 각 테스트 시작 전 엔티티 매니저를 생성
  @BeforeEach
  void setUp() {
    em = JpaUtil.getEntityManager();
    tx = em.getTransaction(); // JPA의 모든 데이터 변경은 트랜잭션 내부에서 실행되어야 함
    tx.begin();
  }

  // 각 테스트 종료 후 엔티티 메니저를 닫아줌
  @AfterEach
  void tearDown() {
    if (tx != null && tx.isActive()) {
      tx.rollback();
    }
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
  @DisplayName("반환 타입이 Query인 JPQL 테스트")
  void queryTests() {
    // 부서 및 사원 등록 
    Department dept = new Department("Develop");
    Employee emp1 = new Employee("jessica",5000);
    Employee emp2 = new Employee("tomcat",6000);
    //부서 사원 등록하기 
    // 연관관계 맺어주기 , 어제는 엔티티 내부에서 한 것이고 오늘은 엔티티 외부에서 진행 부서 사원 등록은 됨. 양방향 관계 만들어야 하니 사원 1,2를 안다 그런데 사원 입장에서는 자기 부서를 모른다
    dept.getEmployees().add(emp1);
    dept.getEmployees().add(emp2);
    // 그래서 dept를 넣어주어야 한다(SetDepartment같은 것이 필요하다)
    // emp1.getDepartment() = dept;
    // 사원에 부서 정보 등록하기 
    emp1.setDePartment(dept);
    emp2.setDePartment(dept);

    // 영속화 
    em.persist(dept);
    em.persist(emp1);
    em.persist(emp2);

    // 쓰기 지연 SQL 저장소의 쿼리를 DB로 날림
    em.flush();
    // em.clear(); JPQL은 무조건 DB로 부터 조회를 하기 때문에 그동안은 clear가 따라온 이유는 find를 sql로 하길 원해서 clear를 한 것이다. sql로 DB로 부터 조회를 하기 위해 사용을 한 것이다. 영속화된 엔티티를 준 영속 상태로 바꾼 것이다. 그런데 JPQL은 필요하지 않다. 
    em.clear();
    
    // 조회 들어가는 JPQL 파트 
    Query query = em.createQuery("select e.name, e.salary from Employee e");
    List<Object[]> results = query.getResultList();
    results.stream().forEach(obj -> {
      Object[] row = (Object[]) obj;
      System.out.println("이름: "+row[0] + " 급여: " + row[1]);
    });
  }



  @Test
  @DisplayName("반환 타입이 TypedQuery인 JPQL 테스트")
  void typedQueryTests() {
        // 부서 및 사원 등록 
    Department dept = new Department("Develop");
    Employee emp1 = new Employee("jessica",5000);
    Employee emp2 = new Employee("tomcat",6000);
    //부서 사원 등록하기 
    // 연관관계 맺어주기 , 어제는 엔티티 내부에서 한 것이고 오늘은 엔티티 외부에서 진행 부서 사원 등록은 됨. 양방향 관계 만들어야 하니 사원 1,2를 안다 그런데 사원 입장에서는 자기 부서를 모른다
    dept.getEmployees().add(emp1);
    dept.getEmployees().add(emp2);
    // 그래서 dept를 넣어주어야 한다(SetDepartment같은 것이 필요하다)
    // emp1.getDepartment() = dept;
    // 사원에 부서 정보 등록하기 
    emp1.setDePartment(dept);
    emp2.setDePartment(dept);

    // 영속화 
    em.persist(dept);
    em.persist(emp1);
    em.persist(emp2);

    // 쓰기 지연 SQL 저장소의 쿼리를 DB로 날림
    em.flush();
    // em.clear(); JPQL은 무조건 DB로 부터 조회를 하기 때문에 그동안은 clear가 따라온 이유는 find를 sql로 하길 원해서 clear를 한 것이다. sql로 DB로 부터 조회를 하기 위해 사용을 한 것이다. 영속화된 엔티티를 준 영속 상태로 바꾼 것이다. 그런데 JPQL은 필요하지 않다. 
    em.clear();

    TypedQuery<Employee> query = em.createQuery("select e from Employee e",Employee.class); // 모든 필드를 조회, emplyee에 있는 클래스의 내용을 전부 가져오겟다는 것. 
    // 결과 행에 따라서 결과 다름 
    List<Employee> employees = query.getResultList();
    employees.stream().forEach(emp -> System.out.println("Name: "+emp.getName()));

   
  }

  @Test
  @DisplayName("N + 1 문제 JPQL")
  void nPlusOneTests() {
    // 부서 및 사원 등록 
    Department dept = new Department("Develop");
    Employee emp1 = new Employee("jessica",5000);
    Employee emp2 = new Employee("tomcat",6000);
    //부서 사원 등록하기 
    // 연관관계 맺어주기 , 어제는 엔티티 내부에서 한 것이고 오늘은 엔티티 외부에서 진행 부서 사원 등록은 됨. 양방향 관계 만들어야 하니 사원 1,2를 안다 그런데 사원 입장에서는 자기 부서를 모른다
    dept.getEmployees().add(emp1);
    dept.getEmployees().add(emp2);
    // 그래서 dept를 넣어주어야 한다(SetDepartment같은 것이 필요하다)
    // emp1.getDepartment() = dept;
    // 사원에 부서 정보 등록하기 
    emp1.setDePartment(dept);
    emp2.setDePartment(dept);

    // 영속화 
    em.persist(dept);
    em.persist(emp1);
    em.persist(emp2);

    // 쓰기 지연 SQL 저장소의 쿼리를 DB로 날림
    em.flush();
    // em.clear(); JPQL은 무조건 DB로 부터 조회를 하기 때문에 그동안은 clear가 따라온 이유는 find를 sql로 하길 원해서 clear를 한 것이다. sql로 DB로 부터 조회를 하기 위해 사용을 한 것이다. 영속화된 엔티티를 준 영속 상태로 바꾼 것이다. 그런데 JPQL은 필요하지 않다. 
    em.clear();
    // 사원 조회 쿼리(ㅂ) 
    TypedQuery<Employee> query = em.createQuery("select e from Employee e",Employee.class); // 모든 필드를 조회, emplyee에 있는 클래스의 내용을 전부 가져오겟다는 것. 
    // 결과 행에 따라서 결과 다름 
    List<Employee> employees = query.getResultList();
    
    // 사원마다 부서를 조회하는 쿼리 (N)
    for (Employee emp : employees) {
      System.out.println("Department Name: "+emp.getDepartment().getDeptname());
    }
  }
  
  @Test
  @DisplayName("N+1문제 해결 JPQL")
  void fetchJoinTests() {
    // 부서 및 사원 등록 
    Department dept = new Department("Develop");
    Employee emp1 = new Employee("jessica",5000);
    Employee emp2 = new Employee("tomcat",6000);
    //부서 사원 등록하기 
    // 연관관계 맺어주기 , 어제는 엔티티 내부에서 한 것이고 오늘은 엔티티 외부에서 진행 부서 사원 등록은 됨. 양방향 관계 만들어야 하니 사원 1,2를 안다 그런데 사원 입장에서는 자기 부서를 모른다
    dept.getEmployees().add(emp1);
    dept.getEmployees().add(emp2);
    // 그래서 dept를 넣어주어야 한다(SetDepartment같은 것이 필요하다)
    // emp1.getDepartment() = dept;
    // 사원에 부서 정보 등록하기 
    emp1.setDePartment(dept);
    emp2.setDePartment(dept);

    // 영속화 
    em.persist(dept);
    em.persist(emp1);
    em.persist(emp2);

    // 쓰기 지연 SQL 저장소의 쿼리를 DB로 날림
    em.flush();
    // em.clear(); JPQL은 무조건 DB로 부터 조회를 하기 때문에 그동안은 clear가 따라온 이유는 find를 sql로 하길 원해서 clear를 한 것이다. sql로 DB로 부터 조회를 하기 위해 사용을 한 것이다. 영속화된 엔티티를 준 영속 상태로 바꾼 것이다. 그런데 JPQL은 필요하지 않다. 
    em.clear();
    //  fetch join으로 한번에 조회하기 
    String jpql = "select e from Employee e join fetch e.department";

    List<Employee> employees = em.createQuery(jpql,Employee.class).getResultList();
    for (Employee emp : employees) {
      System.out.println("Department Name: "+emp.getDepartment().getDeptname());
    }
    

   
  }

}