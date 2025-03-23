package com.nhnacademy.blog.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
@RequiredArgsConstructor
public class MemberUpdateRegister {

    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    private final String mbEmail;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
    private final String mbName;

    @NotBlank(message = "모바일 연락처는 필수 입력 항목입니다.")
    @Pattern(
            regexp = "^01[0-9]-\\d{3,4}-\\d{4}$",
            message = "모바일 연락처는 01X-XXXX-XXXX 형식으로 입력해주세요."
    )
    private final String mbMobile;
}
