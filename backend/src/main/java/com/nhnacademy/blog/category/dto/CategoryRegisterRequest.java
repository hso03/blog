package com.nhnacademy.blog.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

@ToString
public class CategoryRegisterRequest {

    private final Long categoryPid;

    @NotNull
    private final String blogFid;

    @NotNull(message = "토픽이 있어야합니다.")
    private final Integer subTopicId;

    @NotBlank(message = "카테고리 이름을 설정해주세요.")
    private final String categoryName;

    private final Integer categorySec;

    public CategoryRegisterRequest(Long categoryPid, String blogFid, Integer subTopicId, String categoryName, Integer categorySec) {
        this.categoryPid = categoryPid;
        this.blogFid = blogFid;
        this.subTopicId = subTopicId;
        this.categoryName = categoryName;
        this.categorySec = categorySec;
    }

    public Long getCategoryPid() {
        return categoryPid;
    }

    public String getBlogFid() {
        return blogFid;
    }

    public Integer getSubTopicId() {
        return subTopicId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Integer getCategorySec() {
        return categorySec;
    }
}
