package com.example.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.data.domain.Post;

public interface PostRepository extends JpaRepository<Post,Long>{

    // 단건조회 (게시글과 댓글을 조인하여 한번에 조회하도록 JPQL 작성)
  @Query("select p from Post p left join fetch p.comments where p.id = :id")
  Post findPostWithComments(@Param("id") Long id);

  // 제목으로 조회 Page (paging처리)
  // pageable : 페이지 처리용
  Page<Post> findByTitleContaining(String keyword, Pageable pageable);
}
