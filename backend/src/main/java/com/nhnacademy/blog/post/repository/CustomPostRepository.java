package com.nhnacademy.blog.post.repository;

import com.nhnacademy.blog.post.domain.Post;

import java.util.List;

public interface CustomPostRepository {
    List<Post> findPostListFromTopicId(Long topicId);
}
