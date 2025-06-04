package com.nhnacademy.blog.post.repository.impl;

import com.nhnacademy.blog.post.domain.Post;
import com.nhnacademy.blog.post.repository.CustomPostRepository;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class CustomPostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {

    public CustomPostRepositoryImpl(){
        super(Post.class);
    }

    @Override
    public List<Post> findPostListFromTopicId(Long topicId) {
        JPAQuery<String> query = new JPAQuery<>(getEntityManager());
        return List.of();
    }
}
