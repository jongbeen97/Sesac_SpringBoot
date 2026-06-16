package com.example.data.domain;

import java.util.ArrayList;
import java.util.List;

import com.example.data.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false,length = 100)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT NOT NULL ")
  private String content;

  @OneToMany(mappedBy = "post")
  private List<Comment> comments = new ArrayList<>();

  public Post(String title, String content) {
    this.title = title;
    this.content = content;
  }
  // 비즈니스 메서드 
  public void addComment(Comment comment) {
    comments.add(comment);
    comment.setPost(this);
  }

  // 변경 감지를 위한 비즈니스 메서드 
  public void updatePost(String title, String content)
  {
    this.title = title;
    this.content = content;
  }

}
