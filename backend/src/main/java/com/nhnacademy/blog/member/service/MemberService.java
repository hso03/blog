package com.nhnacademy.blog.member.service;
import com.nhnacademy.blog.member.dto.MemberLoginResponse;
import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
import com.nhnacademy.blog.member.dto.MemberResponse;
import com.nhnacademy.blog.member.dto.MemberUpdateRegister;

public interface MemberService {
    //회원(등록)
    MemberResponse registerMember(MemberRegisterRequest memberRegisterRequest);
    MemberResponse getMember(long mbNo);
    MemberLoginResponse getMemberByEmail(String mbEmail);
    MemberResponse updateMember(Long mbNo, MemberUpdateRegister memberUpdateRegister);
    MemberResponse withdrawalMember(Long mbNo);
}
