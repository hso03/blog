package com.nhnacademy.blog.post.repository;

import com.nhnacademy.blog.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByBlog_BlogIdOrderByCreatedAtDesc(Long blogBlogId);

    List<Post> findAllByOrderByCreatedAtDesc();
}
//qdsl, pagable