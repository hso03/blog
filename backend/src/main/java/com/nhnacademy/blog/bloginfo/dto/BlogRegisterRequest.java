package com.nhnacademy.blog.bloginfo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.ToString;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

@Value
@ToString
public class BlogRegisterRequest {

    @NotBlank(message = "대표아이디는 필수 입력 항목 입니다.")
    private final String blogFid;

    @NotNull(message = "대표 블로그 설정이 필수 입니다.")
    private final boolean blogMain;

    @NotBlank(message = "닉네임은 필수 입력 항목 입니다.")
    private final String blogName;

    @NotBlank(message = "닉네임은 필수 입력 항목 입니다.")
    private final String blogNickName;

    @Size(max = 200, message = "200자 이하로 작성해주세요.")
    private final String blogDescription;
}
