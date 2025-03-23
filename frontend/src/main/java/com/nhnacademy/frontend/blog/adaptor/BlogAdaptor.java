package com.nhnacademy.frontend.blog.adaptor;

import com.nhnacademy.frontend.blog.dto.BlogRegisterRequest;
import com.nhnacademy.frontend.blog.dto.BlogResponse;
import com.nhnacademy.frontend.blog.dto.BlogUpdateRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "blogAdaptor", url = "http://localhost:8080/api/v1/blogs")
public interface BlogAdaptor {

    @PostMapping
    ResponseEntity<BlogResponse> postBlog(@Validated @RequestBody BlogRegisterRequest blogRegisterRequest);

    @GetMapping("/{blog-Fid}")
    ResponseEntity<BlogResponse> getBlog(@PathVariable("blog-Fid") String blogFid);

    @PutMapping("/{blog-Fid}")
    ResponseEntity<BlogResponse> putBlog(@PathVariable("blog-Fid") String blogFid, @RequestBody BlogUpdateRequest blogUpdateRequest);

    @DeleteMapping("/{blog-Fid}")
    ResponseEntity<Void> deleteBlog(@PathVariable("blog-Fid") @RequestBody String blogFid);

    @GetMapping("/blogmember/{member-id}")
    ResponseEntity<String> getMainBlogFid(@PathVariable("member-id") Long mbNo);

}
