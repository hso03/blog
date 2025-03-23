package com.nhnacademy.blog.postcategory.repository;

import com.nhnacademy.blog.post.domain.Post;
import com.nhnacademy.blog.postcategory.domain.PostCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostCategoryRepository extends JpaRepository<PostCategoryMapping, Long> {
    Optional<PostCategoryMapping> findPostCategoryMappingsByPost(Post post);
}
