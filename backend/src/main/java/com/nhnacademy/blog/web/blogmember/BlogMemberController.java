package com.nhnacademy.blog.web.blogmember;

import com.nhnacademy.blog.blogmember.dto.BlogMemberMappingResponse;
import com.nhnacademy.blog.blogmember.repository.BlogMemberMappingRepository;
import com.nhnacademy.blog.blogmember.service.BlogMemberMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blogmembers")
@RequiredArgsConstructor
public class BlogMemberController {

    private final BlogMemberMappingService blogMemberMappingService;

    @GetMapping("{member-id}")
    public ResponseEntity<List<BlogMemberMappingResponse>> getBlogMember(@PathVariable("member-id")Long mbNo){
        List<BlogMemberMappingResponse> blogMemberMappingResponseList = blogMemberMappingService.getBlogMemberByMemberNo(mbNo);
        return ResponseEntity
                .ok(blogMemberMappingResponseList);
    }

}