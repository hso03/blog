package com.nhnacademy.blog.post.dto;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.bloginfo.dto.BlogResponse;
import com.nhnacademy.blog.category.dto.CategoryResponse;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.dto.MemberResponse;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@EqualsAndHashCode
public class PostResponse {
    private final Long postId;
    private final String blogFid;
    private final String parentCategoryName;
    private final String categoryName;
    private final String topicName;
    private final Long createdMemberNo;
    private final Long updatedMemberNO;
    private final String postTitle;
    private final String postContent;
    private final Boolean postIsPublic;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
