package com.example.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper // 나는 xml 매퍼 호출할 때 사용하는 인터페이스 
public interface UserMapper {
  long countAll(); // xml mapper에서 id="countAll"인 쿼리 실행 ,태그아이디랑 메서드 이름 맞추면 된다. 


}
