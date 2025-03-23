package com.nhnacademy.blog.bloginfo.service;

import com.nhnacademy.blog.bloginfo.dto.BlogRegisterRequest;
import com.nhnacademy.blog.bloginfo.dto.BlogResponse;
import com.nhnacademy.blog.bloginfo.dto.BlogUpdateRequest;

public interface BlogService {
    BlogResponse getBlogByFid(String blogFid);
    BlogResponse updateBlog(BlogUpdateRequest blogUpdateRequest);
    BlogResponse createBlog(BlogRegisterRequest blogRegisterRequest);
    void deleteBlog(String blogFid);
}
