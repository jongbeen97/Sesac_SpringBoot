package com.example.mybatis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mybatis.dto.PageResponse;
import com.example.mybatis.dto.PostCreateRequest;
import com.example.mybatis.dto.PostResponse;
import com.example.mybatis.dto.PostUpdateRequest;
import com.example.mybatis.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor // service 주입을 받아야 하니 필요함.
@RequestMapping("/api/posts")
public class Postcontroller {

  private final PostService postService;

  // 생성용 controller
  @PostMapping
  public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostCreateRequest request) {
    PostResponse response = postService.createPost(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  // 단건 조회용 controller
  @GetMapping("/{id}") // 조회할 때는 GetMapping이다
  public ResponseEntity<PostResponse> getPost(@PathVariable("id") Long id) {
    return ResponseEntity.ok(postService.findById(id));
  }

  // update 용 controller
  @PutMapping("/{id}")
  public ResponseEntity<PostResponse> updatePost(
      @PathVariable("id") Long id,
      @Valid @RequestBody PostUpdateRequest request) {

        PostResponse response = postService.updatePost(id, request);
        return ResponseEntity.ok(response);
  }

  // delete 용 controller
  @DeleteMapping("/{id}")
 public ResponseEntity<Void> deletePost(
  @PathVariable("id") Long id
)
{
  postService.deletePost(id);
  return ResponseEntity.noContent().build();
}


  // paging 관련 Controller
  @GetMapping
  public ResponseEntity<PageResponse<PostResponse>> getPosts(
      @RequestParam(value = "page", defaultValue = "1") int page,
      @RequestParam(value = "size", defaultValue = "2") int size,
      @RequestParam(value = "sort", defaultValue = "DESC") String sort) {

    postService.getPosts(page, size, sort);

    return ResponseEntity.ok(postService.getPosts(page, size, sort));
  }

}
