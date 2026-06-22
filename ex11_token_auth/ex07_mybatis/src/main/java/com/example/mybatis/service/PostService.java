package com.example.mybatis.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.mybatis.domain.Post;
import com.example.mybatis.dto.PageResponse;
import com.example.mybatis.dto.PostCreateRequest;
import com.example.mybatis.dto.PostResponse;
import com.example.mybatis.dto.PostUpdateRequest;
import com.example.mybatis.exception.CustomException;
import com.example.mybatis.exception.ErrorCode;
import com.example.mybatis.mapper.PostMapper;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true, rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor // 생성자 주입을 통해 필수 코드를채워주는 것
public class PostService {

  private final PostMapper postMapper;

  // 게시글 생성 service 비즈니스 로직 
  @Transactional
  public PostResponse createPost(PostCreateRequest request) {
    Post post = Post.builder()
        .userId(request.userId())
        .title(request.title())
        .content(request.content())
        .build();
    System.out.println("INSERT 이전 Post : " + post);
    // 제약 조건 위배를 대비한 코드 필요
    postMapper.save(post);

    System.out.println("INSERT 이후 Post : " + post);

    return findById(post.getId());
  }

  // ID 조회용 service
  public PostResponse findById(Long id) {
    Post post = postMapper.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    return PostResponse.from(post); // post로부터 postResponse 얻기 정적 메서드 패턴 중 하나
  }

  // 게시글 수정 service
  @Transactional
  public PostResponse updatePost(Long id, PostUpdateRequest request) {

    // 수정 게시글 존재 검증 
    Post post = postMapper.findById(id)
      .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    // 빌더 패턴 사용하여 데이터 수정된 내용을 반영한 새 객체 생성 
    Post updatePost = Post.builder()
        .id(post.getId())
        .userId(post.getUserId())
        .title(request.title())
        .content(request.content())
        .build();

        // DB 업데이트 쿼리 실행 
        postMapper.update(updatePost);

        // 수정 완료 된 최신 데이터 조회 반환 
        return findById(id);
  }

  // 게시글 삭제 service
  @Transactional
  public void deletePost(Long id) {
    // 게시글 존재 검증 
        Post post = postMapper.findById(id)
      .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

    postMapper.deleteById(post.getId());
  }




  // 페이징 관련 service
  public PageResponse<PostResponse> getPosts(int page, int size, String sort) {
    long offset = (page - 1) * size;
    long totalElements = postMapper.countAll();
    int totalPages = (int) Math.ceil((double) totalElements / size);

    List<Post> posts = postMapper.findAll(offset, size, sort);
    List<PostResponse> contents = posts.stream()
        .map(post -> PostResponse.from(post)) // .map(PostResponse::from) 파라미터에 메서드 입력이 전부라면 , 메서드 참조 문법 , 함수형 프로그래밍
        .collect(Collectors.toList());

    return new PageResponse<>(contents, page, size, totalPages, totalElements, sort);
  }

}
