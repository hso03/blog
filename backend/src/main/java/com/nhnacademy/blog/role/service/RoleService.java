package com.nhnacademy.blog.role.service;

import com.nhnacademy.blog.role.dto.RoleRregisterRequeset;
import com.nhnacademy.blog.role.dto.RoleResponse;

public interface RoleService {
    RoleResponse registerRole(RoleRregisterRequeset roleRequest);
    RoleResponse getRoleId(String roleId);
    RoleResponse getRoleName(String roleName);
}
