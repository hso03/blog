package com.nhnacademy.blog.web.post;


import com.nhnacademy.blog.post.domain.Post;
import com.nhnacademy.blog.post.dto.PostRegisterRequest;
import com.nhnacademy.blog.post.dto.PostResponse;
import com.nhnacademy.blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/{blog-fid}")
    public ResponseEntity<PostResponse> registerPost(@Validated @RequestBody PostRegisterRequest postRegisterRequest, @PathVariable("blog-fid") String blogFid, @RequestParam Long mbNo){
        PostResponse postResponse = postService.registerPost(postRegisterRequest, blogFid, mbNo);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postResponse);
    }

    @GetMapping("/{blog-fid}")
    public ResponseEntity<List<PostResponse>> getPost(@PathVariable("blog-fid") String blogFid){
        List<PostResponse> postResponseList = postService.getListPostByBlogFid(blogFid);

        return ResponseEntity
                .ok(postResponseList);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getPostListAll(){
        List<PostResponse> postResponseList = postService.getListPostByAll();

        return ResponseEntity
                .ok(postResponseList);
    }

}
