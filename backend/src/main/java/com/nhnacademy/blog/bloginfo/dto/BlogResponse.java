package com.nhnacademy.blog.bloginfo.dto;

import groovy.transform.ToString;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@EqualsAndHashCode
@ToString
public class BlogResponse {
    private final Long blogId;
    private final String blogFid;
    private final boolean blogMain;
    private final String blogName;
    private final String blogMbNickname;
    private final String blogDescription;
    private final LocalDateTime createAt;
    private final LocalDateTime updateAt;
    private final Boolean blogPublic;
}