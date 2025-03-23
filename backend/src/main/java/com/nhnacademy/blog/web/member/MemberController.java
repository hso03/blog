package com.nhnacademy.blog.web.member;

import com.nhnacademy.blog.common.exception.NotFoundException;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.dto.MemberLoginResponse;
import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
import com.nhnacademy.blog.member.dto.MemberResponse;
import com.nhnacademy.blog.member.dto.MemberUpdateRegister;
import com.nhnacademy.blog.member.repository.MemberRepository;
import com.nhnacademy.blog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * TODO#10 이전 step에서 구현했던 MemberController 입니다. 이를 참고하여 회원가입을 구현 합니다.
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@Validated @RequestBody MemberRegisterRequest memberRegisterRequest){
        MemberResponse memberResponse = memberService.registerMember(memberRegisterRequest);
        log.error("memberResponse! : {}", memberResponse);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberResponse);
    }

    @GetMapping("/{member-id}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable("member-id") Long mbNo){
        MemberResponse memberResponse = memberService.getMember(mbNo);
        return ResponseEntity.ok(memberResponse);
    }

    @PutMapping("/{member-id}")
    public ResponseEntity<MemberResponse> updateMember(
            @PathVariable("member-id") Long mbNo, @Validated @RequestBody MemberUpdateRegister memberUpdateRegister){
        MemberResponse memberResponse = memberService.updateMember(mbNo, memberUpdateRegister);
        return ResponseEntity.ok(memberResponse);
    }

    @DeleteMapping("/{member-id}")
    public ResponseEntity<MemberResponse> deleteMember(@PathVariable("member-id") Long mbNo){
        MemberResponse memberResponse = memberService.withdrawalMember(mbNo);
        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping("/email/{member-email}")
    public ResponseEntity<MemberLoginResponse> getMemberByEmail(@PathVariable("member-email") String email){
        MemberLoginResponse memberLoginResponse = memberService.getMemberByEmail(email);
        return ResponseEntity
                .ok(memberLoginResponse);
    }
}
