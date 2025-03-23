package com.nhnacademy.blog.web.blog;

import com.nhnacademy.blog.bloginfo.dto.BlogRegisterRequest;
import com.nhnacademy.blog.bloginfo.dto.BlogResponse;
import com.nhnacademy.blog.bloginfo.dto.BlogUpdateRequest;
import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.bloginfo.service.BlogService;
import com.nhnacademy.blog.blogmember.domain.BlogMemberMapping;
import com.nhnacademy.blog.blogmember.repository.BlogMemberMappingRepository;
import com.nhnacademy.blog.common.exception.NotFoundException;
import com.nhnacademy.blog.member.dto.MemberResponse;
import com.nhnacademy.blog.member.service.MemberService;
import com.nhnacademy.blog.post.domain.Post;
import com.nhnacademy.blog.post.dto.PostRegisterRequest;
import com.nhnacademy.blog.post.service.PostService;
import com.nhnacademy.blog.topic.dto.TopicResponse;
import com.nhnacademy.blog.topic.repository.TopicRepository;
import com.nhnacademy.blog.topic.service.TopicService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * TODO#8 구현 1순위 - 사용자의 블로그를 구현 합니다.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/blogs")
@AllArgsConstructor
public class BlogIndexController {

    private final BlogService blogServiceImpl;
    private final PostService postServiceImpl;
    private final TopicService topicService;
    private final BlogRepository blogRepository;

    @PostMapping
    public ResponseEntity<BlogResponse> postBlog(@Validated @RequestBody BlogRegisterRequest blogRegisterRequest){
        BlogResponse blogResponse = blogServiceImpl.createBlog(blogRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(blogResponse);
    }

    @GetMapping("/{blog-Fid}")
    public ResponseEntity<BlogResponse> getBlog(@PathVariable("blog-Fid") String blogFid){
        BlogResponse blogResponse = blogServiceImpl.getBlogByFid(blogFid);
        return ResponseEntity
                .ok(blogResponse);
    }

    @PutMapping("/{blog-Fid}")
    public ResponseEntity<BlogResponse> putBlog(@PathVariable("blog-Fid") String blogFid, @Validated @RequestBody BlogUpdateRequest blogUpdateRequest){
        BlogResponse blogResponse = blogServiceImpl.updateBlog(blogUpdateRequest);
        return ResponseEntity
                .ok(blogResponse);
    }

    @DeleteMapping("/{blog-Fid}")
    public ResponseEntity<Void> deleteBlog(@PathVariable("blog-Fid") @RequestBody String blogFid){
        blogServiceImpl.deleteBlog(blogFid);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    @GetMapping("/blogmember/{member-id}")
    public ResponseEntity<String> getMainBlogFid(@PathVariable("member-id") Long mbNo){
        String blogFid = blogRepository.findBlogFidFromMainBlog(mbNo);
        return ResponseEntity
                .ok(blogFid);
    }
}