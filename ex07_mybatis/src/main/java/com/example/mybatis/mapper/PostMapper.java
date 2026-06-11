package com.example.mybatis.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.mybatis.domain.Post;

@Mapper
public interface PostMapper {
  long countAll();
  Optional<Post> findById(Long id); // 여기서 옵셔널로 쌓아서 서비스단으로 넘긴다.
  List<Post> findAll(@Param("offset") long offset, @Param("size") int size); // 전달하는 파라미터가 2개 이상일 때는 Param을 붙여야 한다. 
  int save(Post post);
  int update(Post post);
  int deleteById(Long id);
}
