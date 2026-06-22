package com.example.valid_teacher.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.valid_teacher.dto.MemberCreateRequest;
import com.example.valid_teacher.dto.MemberDto;
import com.example.valid_teacher.dto.MemberUpdateRequest;
import com.example.valid_teacher.exception.CustomException;
import com.example.valid_teacher.exception.ErrorCode;

@Service
public class MemberService {

  private final Map<Long, MemberDto> store = new ConcurrentHashMap<>();
  private final AtomicLong sequence = new AtomicLong(0);

  public MemberService() {
    save(MemberCreateRequest.builder().username("kim").email("kim@test.com").build());
    save(MemberCreateRequest.builder().username("lee").email("lee@test.com").build());
    save(MemberCreateRequest.builder().username("choi").email("choi@test.com").build());
  }

  // Save
  public MemberDto save(MemberCreateRequest request) {
    // 이메일 중복 검증
    boolean isExistEmail = store.values().stream()
        .anyMatch(member -> member.email().equals(request.email()));
    if (isExistEmail) {
      throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
    }

    Long id = sequence.incrementAndGet();
    MemberDto member = MemberDto.builder()
        .id(id)
        .username(request.username())
        .email(request.email())
        .createdAt(LocalDateTime.now())
        .build();
    store.put(id, member);
    return member;
  }

  // Read All
  public List<MemberDto> findAll() {
    return new ArrayList<>(store.values());
  }

  // Read One
  public MemberDto findById(Long id) {
    MemberDto foundMember = store.get(id);
    // 없는 회원 예외 처리
    if (foundMember == null) {
      throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
    }
    return foundMember;
  }

  // Update
  public MemberDto updateMember(Long id, MemberUpdateRequest request) {
    MemberDto foundMember = findById(id);
    MemberDto updatedMember = MemberDto.builder()
        .id(foundMember.id())
        .username(foundMember.username())
        .email(request.email())
        .createdAt(foundMember.createdAt())
        .build();
    store.put(id, updatedMember);
    return updatedMember;
  }

  // Delete
  public void deleteById(Long id) {
    findById(id);
    store.remove(id);
  }
}
