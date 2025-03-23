package com.nhnacademy.blog.bloginfo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;

@ToString
public class BlogUpdateRequest {

    @NotBlank(message = "대표아이디는 필수 입력 항목 입니다.")
    private final String blogFid;

    @NotBlank(message = "닉네임은 필수 입력 항목 입니다.")
    private final String blogMbNickname;

    @NotBlank(message = "이름은 필수 입력 항목 입니다ㅏ.")
    private final String blogName;

    @Size(max = 200, message = "200자 이하로 작성해주세요.")
    private final String blogDescription;

    public BlogUpdateRequest(String blogFid, String blogMbNickname, String blogName, String blogDescription) {
        this.blogFid = blogFid;
        this.blogMbNickname = blogMbNickname;
        this.blogName = blogName;
        this.blogDescription = blogDescription;
    }

    public String getBlogFid() {
        return blogFid;
    }

    public String getBlogMbNickname() {
        return blogMbNickname;
    }

    public String getBlogName() {
        return blogName;
    }

    public String getBlogDescription() {
        return blogDescription;
    }

}
