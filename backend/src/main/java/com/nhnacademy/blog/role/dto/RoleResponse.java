package com.nhnacademy.blog.role.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@AllArgsConstructor
@EqualsAndHashCode
public class RoleResponse {

    private final String roleId;

    private final String roleName;

    private final String roleDescription;
}
