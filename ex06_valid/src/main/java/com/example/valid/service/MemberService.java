package com.example.valid.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.valid.dto.MemberCreateRequest;
import com.example.valid.dto.MemberDTO;
import com.example.valid.dto.MemberUpdateRequest;
import com.example.valid.exception.CustomException;
import com.example.valid.exception.ErrorCode;

@Service
public class MemberService {

  private final Map<Long, MemberDTO> store = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong(0);

  // 생성자 생성 
  public MemberService(){
    save(MemberCreateRequest.builder().username("kim").email("kim@test.com").build());
    save(MemberCreateRequest.builder().username("lee").email("lee@test.com").build());
    save(MemberCreateRequest.builder().username("park").email("park@test.com").build());
  }

  // Member Save 
  public MemberDTO save(MemberCreateRequest request){
    // 이메일 중복 검증 방식 
    boolean isExistEmail = store.values().stream()
      .anyMatch(member ->  member.email().equals(request.email()));

    if(isExistEmail){
      throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
    }

    // 예외 처리 코드는 나중에 넣을 것. 
    Long id = sequence.incrementAndGet(); // 증가 후 가져오기
    // builder 객체를 이용하여 저장하기  
    MemberDTO member = MemberDTO.builder()
      .id(id)
      .username(request.username())
      .email(request.email())
      .createdAt(LocalDateTime.now())
      .build();
  store.put(id, member);
  return member;
  }

  // Read All 
  public List<MemberDTO> findAll(){
    return new ArrayList<>(store.values());
  }

  // Read One
  public MemberDTO findById(Long id){
    // 회원 조회 하기 
    MemberDTO foundMember = store.get(id);
    // 없는 회원은 예외 처리하기 
    if(foundMember == null){
      throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
    }

    // 있다면 찾은 회원 반환 
    return foundMember;
  }

  // Update 
  public MemberDTO updateMember(Long id , MemberUpdateRequest request){
    // 회원 조회 
    MemberDTO foundMember = findById(id);
    // 조회된 결과 업데이트 할 맴버 선택 
    MemberDTO updateMember = MemberDTO.builder()
        .id(foundMember.id())
        .username(foundMember.username())
        .email(request.email())
        .createdAt(foundMember.createdAt())
        .build();
      store.put(id, updateMember);
      return updateMember;
  }

  // Delete
  public void deleteById(Long id){
    findById(id);
    store.remove(id);
  }

}
