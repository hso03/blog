package com.nhnacademy.blog.category.dto;

import java.time.LocalDateTime;

public class CategoryResponse {

    private final Long categoryId;

    private final Long categoryPid;

    private final String blogFid;

    private final Integer topicId;

    private final String categoryName;

    private final Integer categorySec;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public CategoryResponse(Long categoryId, Long categoryPid, String blogFid, Integer topicId, String categoryName, Integer categorySec, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.categoryId = categoryId;
        this.categoryPid = categoryPid;
        this.blogFid = blogFid;
        this.topicId = topicId;
        this.categoryName = categoryName;
        this.categorySec = categorySec;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getCategoryPid() {
        return categoryPid;
    }

    public String getBlogFid() {
        return blogFid;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Integer getCategorySec() {
        return categorySec;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
