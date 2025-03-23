package com.nhnacademy.frontend.post.adptor;

import com.nhnacademy.frontend.post.dto.PostRegisterRequest;
import com.nhnacademy.frontend.post.dto.PostResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "postAdaptor", url = "http://localhost:8080/api/v1/posts")
public interface PostAdaptor {

    @PostMapping("/{blog-fid}")
    ResponseEntity<PostResponse> registerPost(@Validated @RequestBody PostRegisterRequest postRegisterRequest, @PathVariable("blog-fid") String blogFid, @RequestParam Long mbNo);

    @GetMapping("/{blog-fid}")
    ResponseEntity<List<PostResponse>> getPost(@PathVariable("blog-fid") String blogFid);

    @GetMapping
    ResponseEntity<List<PostResponse>> getPostListAll();
}
