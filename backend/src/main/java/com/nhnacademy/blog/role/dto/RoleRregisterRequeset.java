package com.nhnacademy.blog.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class RoleRregisterRequeset {

    @NotBlank(message = "권한 아이디를 입력하세요.")
    private final String roleId;

    @NotBlank(message = "권한 이름을 입력하세요.")
    private final String roleName;

    private final String roleDescription;

}
