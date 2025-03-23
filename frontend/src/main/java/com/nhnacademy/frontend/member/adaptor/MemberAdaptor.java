package com.nhnacademy.frontend.member.adaptor;

import com.nhnacademy.frontend.member.dto.MemberLoginResponse;
import com.nhnacademy.frontend.member.dto.MemberRegisterRequest;
import com.nhnacademy.frontend.member.dto.MemberResponse;
import com.nhnacademy.frontend.member.dto.MemberUpdateRegister;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "memberAdaptor" , url = "http://localhost:8080/api/v1/members")
public interface MemberAdaptor {

    @PostMapping
    ResponseEntity<MemberResponse> createMember(@Validated @RequestBody MemberRegisterRequest memberRegisterRequest);

    @GetMapping(value = "/{member-id}")
    ResponseEntity<MemberResponse> getMember(@PathVariable("member-id") Long mbNo);

    @PutMapping("/{member-id}")
    ResponseEntity<MemberResponse> updateMember(@PathVariable("member-id") Long mbNo, @Validated @RequestBody MemberUpdateRegister memberUpdateRegister);

    @DeleteMapping("/{member-id}")
    ResponseEntity<MemberResponse> deleteMember(@PathVariable("member-id") Long mbNo);

    @GetMapping("/email/{member-email}")
    ResponseEntity<MemberLoginResponse> getMemberByEmail(@PathVariable("member-email") String email);
}
