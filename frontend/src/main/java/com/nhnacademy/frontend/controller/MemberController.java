package com.nhnacademy.frontend.controller;

import com.nhnacademy.frontend.member.dto.MemberRegisterRequest;
import com.nhnacademy.frontend.member.dto.MemberResponse;
import com.nhnacademy.frontend.member.service.MemberService;
import com.nhnacademy.frontend.security.userdetail.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/register.do")
    public String register(Model model) {
        return "member/register";
    }

    @GetMapping("/{member-id}")
    public String find(@PathVariable("member-id") Long mbNo, Model model){
        MemberResponse memberResponse = memberService.getMember(mbNo);
        model.addAttribute("memberResponse", memberResponse);
        return "sample";
    }

    @PostMapping(value = "/registerAction.do")
    public String registerAction(@Validated MemberRegisterRequest memberRegisterRequest, Model model) {
        log.info("memberRegisterRequest1: {}", memberRegisterRequest);
        model.addAttribute("memberRegisterRequest", memberRegisterRequest);
        memberService.registerMember(memberRegisterRequest);

        log.info("멤버 : {}", memberRegisterRequest);
        return "member/registerResult";
    }

    @GetMapping("/myinfo.do")
    public String myinfo(Model model, @AuthenticationPrincipal MemberDetails memberDetails) {
        MemberResponse memberResponse = memberService.getMemberByEmail(memberDetails.getUsername());
        model.addAttribute("memberResponse", memberResponse);
        return "member/myinfo";
    }

}
