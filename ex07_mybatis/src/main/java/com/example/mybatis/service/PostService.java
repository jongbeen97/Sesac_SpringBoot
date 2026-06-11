package com.example.mybatis.service;


import org.springframework.stereotype.Service;

import com.example.mybatis.domain.Post;
import com.example.mybatis.dto.PostCreateRequest;
import com.example.mybatis.dto.PostResponse;
import com.example.mybatis.exception.CustomException;
import com.example.mybatis.exception.ErrorCode;
import com.example.mybatis.mapper.PostMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 생성자 주입을 통해 필수 코드를채워주는 것
public class PostService {

  private final PostMapper postMapper;

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

    // post 에는 어떤 정보가 저장이 되어 있는가?
    // userId + title + content 추가로 INSERT 쿼리 실행시 MyBatis가 채운 Id값이 함께 존재함.
    // post 리턴 시 createdAt 제외한 모든 값을 retrun이 가능하다.
    // CreatedAt을 꼭 채워 리턴하고 싶다 ?
    // Select를 하여서 넣는 방법 말고는 없다. 이 부분은 선택으로 두는 것이다. 또 하는 거 말고는 없다.
    // Post의 Id를 이용하여 select 한뒤 결과를 반환한다.
    return findById(post.getId());
  }

  // PostResponse : Post와 타입도 다르기에 중간 변환 과정이 필요한데 일반적으로 변환 과정을 
  public PostResponse findById(Long id) {
    Post post = postMapper.findById(id)
        .orElseThrow(()->new CustomException(ErrorCode.POST_NOT_FOUND));

    /*  if( post == null){
       throw new CustomException(ErrorCode.POST_NOT_FOUND)
     }

     Optional : 상자 , 담는 데이터는 담길수도 있고 안 담길수도 있다. null일수도 있고 아닐 수도 있다. 
     Optional<Post> opt = Optional.ofNullable(post);
     opt.get(); // 100% 데이터 존재한다고 가정할 때 
     opt.orElse(post); // 데이터가 존재하지 않을때 대신 사용할 객체 지정 
     opt.orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));//없으면 예외를 던질 때 있으면 꺼내고 없으면 예외 던지겟다. POST 없을때 예외 처리를 어떻게 하고 싶은가? 
     orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND)) 이런 식으로 , 없으면 POST_NOT_FOUND를 사용한다고 보면 된다. 데이터 있으면 꺼내고 없으면 예외를던진다 
    if(post == null){
     throw new Cus...} 데이터 꺼낼 때 데이터 존재하지 않으면 NULL 처리 를 하겟다는 것

    Optional의 실전 사용 방식 
    PostMapper 가 미리 싸서 OptionalPost로 바꾸는 것 */

    return PostResponse.from(post); // post로부터 postResponse 얻기 정적 메서드 패턴 중 하나 
  }

}
