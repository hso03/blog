package com.nhnacademy.blog.bloginfo.service.impl;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.bloginfo.dto.BlogUpdateRequest;
import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.bloginfo.service.BlogService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("testyml")
class BlogServiceImplTest {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogService blogService;

    Blog blog;

    @BeforeEach
    void setUp() {
        blog = Blog.ofNewBlog("fid", true, "name", "nickname", "description");
        blogRepository.save(blog);
    }

    @Test
    void getBlogByFid() {
    }

    @Test
    void getMember(){

    }

    @Test
    void updateBlog() {
        BlogUpdateRequest blogUpdateRequest = new BlogUpdateRequest(
                "fid", "changeNickname", "changeName", "changeDescription"
        );

        blogService.updateBlog(blogUpdateRequest);

        Optional<Blog> optionalBlog = blogRepository.findByBlogFid("fid");

        Assertions.assertTrue(optionalBlog.isPresent());

        Blog blog = optionalBlog.get();

        Assertions.assertEquals("changeNickname", blog.getBlogMbNickname());
    }
}