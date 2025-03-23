package com.nhnacademy.frontend.blogmember.dto;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class BlogMemberMappingResponse {

    private final Long blogMemberId;

    private final Long memberId;

    private final Long blogId;

    private final String blogFid; //블로그 대표이름 ex) http://localhost:8080/marco/post/1 , marco <-- 블로그 식별자

    private final String roleId;

    private final String roleName; //권한 이름, ex)블로그 소유자

}
