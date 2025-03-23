package com.nhnacademy.frontend.member.service;

import com.nhnacademy.frontend.member.dto.MemberRegisterRequest;
import com.nhnacademy.frontend.member.dto.MemberResponse;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    MemberResponse registerMember(MemberRegisterRequest memberRegisterRequest);
    MemberResponse getMember(Long mbNo);
    MemberResponse getMemberByEmail(String mbEmail);
}
