package com.nhnacademy.blog.role.repository;

import com.nhnacademy.blog.role.doamin.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    boolean existsByRoleId(String roleId);

    boolean existsByRoleName(String roleName);

    Optional<Role> findByRoleId(String roleId);

    Optional<Role> findByRoleName(String roleName);
}
