package com.example.restapi.service;

import java.util.List;

import com.example.restapi.dto.MemberRequest;
import com.example.restapi.dto.MemberResponse;

public interface MemberService {
  MemberResponse save(MemberRequest request);
  List<MemberResponse> findAll();
  MemberResponse  findById(Long id);
  MemberResponse update(MemberRequest request,Long id);
  void deleteById(Long id);
}
