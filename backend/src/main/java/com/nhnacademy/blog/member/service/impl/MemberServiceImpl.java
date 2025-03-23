package com.nhnacademy.blog.member.service.impl;
import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.blogmember.domain.BlogMemberMapping;
import com.nhnacademy.blog.common.exception.ConflictException;
import com.nhnacademy.blog.common.exception.NotFoundException;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.dto.MemberLoginResponse;
import com.nhnacademy.blog.member.dto.MemberRegisterRequest;
import com.nhnacademy.blog.member.dto.MemberResponse;
import com.nhnacademy.blog.member.dto.MemberUpdateRegister;
import com.nhnacademy.blog.member.repository.MemberRepository;
import com.nhnacademy.blog.member.service.MemberService;
import com.nhnacademy.blog.role.doamin.Role;
import com.nhnacademy.blog.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final BlogRepository blogRepository;
    private final RoleRepository roleRepository;

    @Override
    public MemberResponse registerMember(MemberRegisterRequest memberRegisterRequest) {

        //1.이메일 중복체크
        boolean isExistsEmail = memberRepository.existsByMbEmail(memberRegisterRequest.getMbEmail());
        if(isExistsEmail) {
            throw new ConflictException("Member email [%s] already exists".formatted(memberRegisterRequest.getMbEmail()));
        }

        //2. 모바일_연락처 중복체크
        boolean isExistMobile = memberRepository.existsByMbMobile(memberRegisterRequest.getMbMobile());
        if(isExistMobile) {
            throw new ConflictException("Member mobile [%s] already exists".formatted(memberRegisterRequest.getMbMobile()));
        }

        //3. 블로그 대표아이디 체크
        boolean isExistFid = blogRepository.existsByBlogFid(memberRegisterRequest.getBlogFid());
        if(isExistFid){
            throw new ConflictException("Blog Fid [%s] already exists".formatted(memberRegisterRequest.getBlogFid()));
        }

        String mbPassword = passwordEncoder.encode(memberRegisterRequest.getMbPassword());
        Member member = Member.ofNewMember(memberRegisterRequest.getMbEmail(), memberRegisterRequest.getMbName(), mbPassword, memberRegisterRequest.getMbMobile());
        memberRepository.save(member);

        Optional<Member> memberOptional = memberRepository.findById(member.getMbNo());
        if(memberOptional.isPresent()) {

            Blog blog = Blog.ofNewBlog(
                    memberRegisterRequest.getBlogFid(),
                    true,
                    "default",
                    "default",
                    "description");

            Role role = roleRepository.findByRoleId("ROLE_OWNER").orElseThrow(()->new NotFoundException("not found"));

            BlogMemberMapping blogMemberMapping = BlogMemberMapping.ofNewBlogMemberMapping(member, blog, role);
            blog.addBlogMemberMapping(blogMemberMapping);
            blogRepository.save(blog);
            return new MemberResponse(
                    member.getMbNo(),
                    member.getMbEmail(),
                    member.getMbName(),
                    member.getMbMobile(),
                    member.getCreatedAt(),
                    member.getUpdatedAt(),
                    member.getWithdrawalAt()
            );
        }

        throw new NotFoundException("Member not found");
    }

    @Override
    public MemberResponse getMember(long mbNo) {
        Optional<Member> memberOptional = memberRepository.findById(mbNo);
        if(memberOptional.isPresent()) {
            Member member = memberOptional.get();
            return new MemberResponse(
                    member.getMbNo(),
                    member.getMbEmail(),
                    member.getMbName(),
                    member.getMbMobile(),
                    member.getCreatedAt(),
                    member.getUpdatedAt(),
                    member.getWithdrawalAt()
            );
        }
        
        throw new NotFoundException("Member not found");
    }

    @Override
    public MemberLoginResponse getMemberByEmail(String mbEmail) {
        Optional<Member> memberOptional = memberRepository.findByMbEmail(mbEmail);

        if(memberOptional.isEmpty()){
            throw new NotFoundException("Member not found");
        }

        Member member = memberOptional.get();
        return new MemberLoginResponse(
                member.getMbNo(),
                member.getMbEmail(),
                member.getMbPassword(),
                member.getMbName(),
                member.getMbMobile(),
                member.getCreatedAt(),
                member.getUpdatedAt(),
                member.getWithdrawalAt()
        );

    }

    @Override
    public MemberResponse updateMember(Long mbNo, MemberUpdateRegister memberUpdateRegister){
        Member member = memberRepository.findById(mbNo).orElseThrow(()->new NotFoundException("%s not found".formatted(mbNo)));
        member.update(
                memberUpdateRegister.getMbEmail(),
                memberUpdateRegister.getMbName(),
                memberUpdateRegister.getMbMobile()
        );
        // no, email, name, mobile, create, update, withdrwal
        return new MemberResponse(
                member.getMbNo(),
                member.getMbEmail(),
                member.getMbName(),
                member.getMbMobile(),
                member.getCreatedAt(),
                member.getUpdatedAt(),
                member.getWithdrawalAt()
        );
    }
    @Override
    public MemberResponse withdrawalMember(Long mbNo) {
        Member member = memberRepository.findById(mbNo).orElseThrow(() -> new NotFoundException("%s not found".formatted(mbNo)));
        member.withdraw();

        return new MemberResponse(
                member.getMbNo(),
                member.getMbEmail(),
                member.getMbName(),
                member.getMbMobile(),
                member.getCreatedAt(),
                member.getUpdatedAt(),
                member.getWithdrawalAt()
        );
    }
}
