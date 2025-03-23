package com.nhnacademy.blog.role.service.impl;

import com.nhnacademy.blog.common.exception.ConflictException;
import com.nhnacademy.blog.common.exception.NotFoundException;
import com.nhnacademy.blog.role.doamin.Role;
import com.nhnacademy.blog.role.dto.RoleRregisterRequeset;
import com.nhnacademy.blog.role.dto.RoleResponse;
import com.nhnacademy.blog.role.repository.RoleRepository;
import com.nhnacademy.blog.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public RoleResponse registerRole(RoleRregisterRequeset roleRregisterRequeset) {

        //권한 아이디 중복 체크
        boolean isExistRoleId = roleRepository.existsByRoleId(roleRregisterRequeset.getRoleId());
        if(isExistRoleId){
            throw new ConflictException("Role Id already exist");
        }

        // 권한 이름 중복 체크
        boolean isExistRoleName = roleRepository.existsByRoleName(roleRregisterRequeset.getRoleName());
        if(isExistRoleName){
            throw new ConflictException("Role Name already exist");
        }

        Role role = new Role(
                roleRregisterRequeset.getRoleId(),
                roleRregisterRequeset.getRoleName(),
                roleRregisterRequeset.getRoleDescription()
        );

        roleRepository.save(role);

        Optional<Role> optionalRole = roleRepository.findByRoleId(role.getRoleId());
        if(optionalRole.isPresent()){
            return new RoleResponse(
                    role.getRoleId(),
                    role.getRoleName(),
                    role.getRoleDescription()
            );
        }
        throw new NotFoundException("Role not found");
    }

    @Override
    public RoleResponse getRoleId(String roleId) {
        Optional<Role> optionalRole = roleRepository.findByRoleId(roleId);
        if(optionalRole.isPresent()){
            return new RoleResponse(
                    optionalRole.get().getRoleId(),
                    optionalRole.get().getRoleName(),
                    optionalRole.get().getRoleDescription()
            );
        }
        throw new NotFoundException("Role not found");
    }

    @Override
    public RoleResponse getRoleName(String roleName) {
        Optional<Role> optionalRole = roleRepository.findByRoleName(roleName);
        if(optionalRole.isPresent()){
            return new RoleResponse(
                    optionalRole.get().getRoleId(),
                    optionalRole.get().getRoleName(),
                    optionalRole.get().getRoleDescription()
            );
        }
        throw new NotFoundException("Role not found");
    }
}
