package com.example.valid.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.valid.dto.MemberCreateRequest;
import com.example.valid.dto.MemberDTO;
import com.example.valid.dto.MemberUpdateRequest;
import com.example.valid.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberApiController {

  private final MemberService memberService;

  // 1. 등록
  @PostMapping
  public ResponseEntity<MemberDTO> createMember(@Valid @RequestBody MemberCreateRequest request) {
    MemberDTO savedMember = memberService.save(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
  }

  // 2. 전체 조회
  @GetMapping
  public ResponseEntity<List<MemberDTO>> getAllMembers() {
    List<MemberDTO> members = memberService.findAll();
    return ResponseEntity.ok(members);
  }

  // 3. 단건 조회
  @GetMapping("/{id}")
  public ResponseEntity<MemberDTO> getMemberById(@PathVariable("id") Long id) {
    MemberDTO foundMember = memberService.findById(id);
    return ResponseEntity.ok(foundMember);
  }

  // 4. 수정
  @PutMapping("/{id}")
  public ResponseEntity<MemberDTO> updateMember(
      @PathVariable("id") Long id,
      @Valid @RequestBody MemberUpdateRequest request) {
    MemberDTO updatedMember = memberService.updateMember(id, request);
    return ResponseEntity.ok(updatedMember);
  }

  // 5. 회원 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) {
    memberService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}