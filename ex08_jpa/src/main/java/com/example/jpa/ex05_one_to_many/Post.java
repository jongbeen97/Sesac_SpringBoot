package com.example.jpa.ex05_one_to_many;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @OneToMany
  @JoinColumn(name = "post_id") // Post가 아닌 PostComment 테이블(자식 테이블)에 생성할 FK 칼럼명 작성
  private List<PostComment> comments = new ArrayList<>();

  public Post(String title) {
    this.title = title;
  }

  // 비즈니스 편의 상 만든 메서드
  public void addComment(PostComment comment) {
    this.comments.add(comment);
  }

  @Override
  public String toString() {
    return "Post [id=" + id + ", title=" + title + ", comments=" + comments + "]";
  }
}