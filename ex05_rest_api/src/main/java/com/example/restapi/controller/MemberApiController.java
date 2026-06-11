package com.example.restapi.controller;

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

import com.example.restapi.dto.MemberRequest;
import com.example.restapi.dto.MemberResponse;
import com.example.restapi.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberApiController {
  private final MemberService memberService ;

  @PostMapping
  public ResponseEntity<MemberResponse> createMember(@RequestBody MemberRequest request){
   MemberResponse savedMember = memberService.save(request);
   return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
  }

  // 회원 전체 조회
  @GetMapping
  public ResponseEntity<List<MemberResponse>> getAllMembers(){
    List<MemberResponse> members = memberService.findAll();
    return ResponseEntity.ok(members);
  }

  // 단건 조회 방식 
  @GetMapping("/{id}")
  public ResponseEntity<MemberResponse> getMemberById(@PathVariable("id") Long id){
    try {
      MemberResponse foundMember = memberService.findById(id);
      return ResponseEntity.ok(foundMember);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }
  }

  // 수정하기
  @PutMapping("/{id}")
  public ResponseEntity<MemberResponse> updateMember(
    @PathVariable("id") Long id,
    @RequestBody MemberRequest request){
      try {
        MemberResponse updatedMember = memberService.update(request, id);
        return ResponseEntity.ok(updatedMember);
      } catch (Exception e) {
        return ResponseEntity.notFound().build();
      }
  }

  // 회원 삭제하기
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id){
    try {
      memberService.deleteById(id);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }

  }

}
