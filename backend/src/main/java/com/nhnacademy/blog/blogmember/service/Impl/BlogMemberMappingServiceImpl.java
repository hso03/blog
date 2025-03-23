package com.nhnacademy.blog.blogmember.service.Impl;

import com.nhnacademy.blog.blogmember.domain.BlogMemberMapping;
import com.nhnacademy.blog.blogmember.dto.BlogMemberMappingResponse;
import com.nhnacademy.blog.blogmember.repository.BlogMemberMappingRepository;
import com.nhnacademy.blog.blogmember.service.BlogMemberMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BlogMemberMappingServiceImpl implements BlogMemberMappingService {

    private final BlogMemberMappingRepository blogMemberMappingRepository;

    @Override
    public List<BlogMemberMappingResponse> getBlogMemberByMemberNo(Long mbNo) {
        List<BlogMemberMapping> blogMemberMappingList =
                blogMemberMappingRepository.findBlogMemberMappingsByMember_MbNo(mbNo);

        return convertToBlogMemberMappingResponse(blogMemberMappingList);
    }

    private List<BlogMemberMappingResponse> convertToBlogMemberMappingResponse(List<BlogMemberMapping> blogMemberMappinglist){

        List<BlogMemberMappingResponse> blogMemberMappingResponseList = new ArrayList<>();

        // private final Long blogMemberId;
        // private final Long memberId;
        // private final Long blogId;
        // private final String blogFid;
        // private final String roleId;
        // private final String roleName;
        for(BlogMemberMapping blogMemberMapping : blogMemberMappinglist){
            blogMemberMappingResponseList.add(
                    new BlogMemberMappingResponse(
                            blogMemberMapping.getBlogMemberId(),
                            blogMemberMapping.getMember().getMbNo(),
                            blogMemberMapping.getBlog().getBlogId(),
                            blogMemberMapping.getBlog().getBlogFid(),
                            blogMemberMapping.getRole().getRoleId(),
                            blogMemberMapping.getRole().getRoleName()
                    )
            );
        }

        return blogMemberMappingResponseList;
    }
}
