package com.nhnacademy.blog.blogmember.service;

import com.nhnacademy.blog.blogmember.dto.BlogMemberMappingResponse;

import java.util.List;

public interface BlogMemberMappingService {
    List<BlogMemberMappingResponse> getBlogMemberByMemberNo(Long mbNo);
}
