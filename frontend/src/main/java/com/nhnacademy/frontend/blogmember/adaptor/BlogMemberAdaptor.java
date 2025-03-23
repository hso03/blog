package com.nhnacademy.frontend.blogmember.adaptor;

import com.nhnacademy.frontend.blogmember.dto.BlogMemberMappingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "blogMemberAdaptor", url = "http://localhost:8080/api/v1/blogmembers")
public interface BlogMemberAdaptor {

    @GetMapping("{member-id}")
    public ResponseEntity<List<BlogMemberMappingResponse>> getBlogMember(@PathVariable("member-id")Long mbNo);

}
