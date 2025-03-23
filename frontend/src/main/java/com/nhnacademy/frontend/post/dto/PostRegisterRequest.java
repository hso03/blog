package com.nhnacademy.frontend.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class PostRegisterRequest {

    @NotBlank(message = "제목을 입력 하세요.")
    private final String postTitle;

    private final String postContent;

    private final Boolean postIsPublic;

    @NotNull(message = "카테고리 선택은 필수 입니다.")
    private final Long categoryId;
}
