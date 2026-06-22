package com.example.data.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.data.domain.Comment;
import com.example.data.domain.Post;
import com.example.data.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service // service를 담당하는 비즈니스 레이어 빈 등록
@RequiredArgsConstructor // 생성자 주입을 통한 주입
@Transactional(readOnly = true) // springframework것을쓰는 것 , 대부분은 읽기 전용이라 읽기 전용으로 조건을 걸어두는 것이 좋다, 저장 수정은 풀어주도록 할 것
public class PostService {

  private final PostRepository postRepository;

  // 생성
  @Transactional // 읽기 전용이 아니게 되는 것이다. (덮어쓰기 개념)
  public Long createPost(String title, String content) {
    Post post = new Post(title, content); // 생성
    postRepository.save(post); // 정확히 인서트 쿼리 날라가서 실행
    return postRepository.save(post).getId();
  }

  // 단건 조회 
  public Post getPost(Long id) {
    Post post =postRepository.findPostWithComments(id);
    return post;
  }

  // 목록 조회 (페이징, 제목, 키워드 포함)
  public Page<Post> getPosts(String keyword, Pageable pageable){
    if(keyword != null && !keyword.isBlank()){
      postRepository.findByTitleContaining(keyword, pageable); // 검색어가 오면 검색하겟다 
    } // 검색어가 없다면 전체조회로 간다 
    return postRepository.findAll(pageable);
    }

  // 수정 
  @Transactional
  public void updatePost(Long id, String title, String content) {
    Post findPost = postRepository.findById(id)
        .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다.")); 
    findPost.updatePost(title, content);
  }

  // 삭제(수정과 동일하게 조회 후 삭제 ) 
  @Transactional
  public void deletePost(Long id) {
        Post findPost = postRepository.findById(id)
        .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다.")); 
        postRepository.delete(findPost);
  }
  // 댓글 등록 
  @Transactional
  public void addComment(Long postId, String content) {
    Post post = postRepository.findById(postId)
        .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));
    Comment comment = new Comment(content);
    post.addComment(comment);
  }
  }

