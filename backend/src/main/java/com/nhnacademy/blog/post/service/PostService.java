package com.nhnacademy.blog.post.service;

import com.nhnacademy.blog.post.dto.PostRegisterRequest;
import com.nhnacademy.blog.post.dto.PostResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface PostService {

    PostResponse registerPost(PostRegisterRequest postRegisterRequest, String blogFid, Long mbNo);
    List<PostResponse> getListPostByBlogFid(String blogFid);
    List<PostResponse> getListPostByAll();
}
