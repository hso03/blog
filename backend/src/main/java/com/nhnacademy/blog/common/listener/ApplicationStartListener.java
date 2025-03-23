package com.nhnacademy.blog.common.listener;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.blogmember.domain.BlogMemberMapping;
import com.nhnacademy.blog.category.domain.Category;
import com.nhnacademy.blog.category.repository.CategoryRepository;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.repository.MemberRepository;
import com.nhnacademy.blog.role.doamin.Role;
import com.nhnacademy.blog.role.repository.RoleRepository;
import com.nhnacademy.blog.topic.domain.Topic;
import com.nhnacademy.blog.topic.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * TODO#3 local환경에서의 테스트 데이터 생성
 * Spring Application 시작시 블로그 회원가입 블로그 생성
 * - ApplicationReadyEvent 를 구독 합니다.
 * - erd를 기반으로 기본데이터를 생성합니다.
 * - 데이터는 h2에 저장됩니다.
 */

@Slf4j
@Profile("testyml")
@Component
@RequiredArgsConstructor
public class ApplicationStartListener implements ApplicationListener<ApplicationReadyEvent> {

    private final TopicRepository topicRepository;
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        //TODO#3-1 local에서 테스트를 위해서 사용할 이메일/비밀번호 설정
        String username = "marco@nhnacademy.com";
        String password = "nhnacademy";

        //TODO#3-2 해당 username이 존재하면 return; 호출 합니다.
        boolean isExist = memberRepository.existsByMbEmail(username);
        if(isExist) {
            return;
        }

        //TODO#3-3 topic 등록
        Topic topic = Topic.ofNewRootTopic("spring",1);
        Topic topic1 = Topic.ofNewRootTopic("winter",1);
        Topic topic2 = Topic.ofNewRootTopic("summer",1);
        Topic topic3 = Topic.ofNewRootTopic("fall",1);
        Topic springTopic1 = Topic.ofNewSubTopic(topic, "subspinrg1",1);
        Topic springTopic2 = Topic.ofNewSubTopic(topic, "subspinrg2",2);
        Topic springTopic3 = Topic.ofNewSubTopic(topic, "subspinrg3",3);
        Topic winterTopic1 = Topic.ofNewSubTopic(topic1, "subwinter1",1);
        Topic winterTopic2 = Topic.ofNewSubTopic(topic1, "subwinter2",2);
        Topic winterTopic3 = Topic.ofNewSubTopic(topic1, "subwinter3",3);

        topicRepository.save(topic);
        topicRepository.save(topic1);
        topicRepository.save(topic2);
        topicRepository.save(topic3);
        topicRepository.save(springTopic1);
        topicRepository.save(springTopic2);
        topicRepository.save(springTopic3);
        topicRepository.save(winterTopic1);
        topicRepository.save(winterTopic2);
        topicRepository.save(winterTopic3);



        //TODO#3-4 ROLE 등록
        Role role = new Role("ROLE_OWNER", "블로그_소유자", "블로그의 소유자 , 운영자");
        Role role1 = new Role("ROLE_MEMBER", "블로그 회원", "회원");
        roleRepository.save(role);
        roleRepository.save(role1);

        //TODO#3-5 member 등록
        Member member = Member.ofNewMember(
                username,
                "마르코",
                passwordEncoder.encode(password),
                "01011112222"
        );

        Member member1 = Member.ofNewMember(
                "test@naver.com",
                "마르코",
                passwordEncoder.encode("1"),
                "01011113333"
        );

        memberRepository.save(member);
        memberRepository.save(member1);

        //TODO#3-6 blog 등록
        Blog blog = Blog.ofNewBlog(
                "marco",
                true,
                "marco's blog",
                "marco",
                "Spring Blog!"
        );

        Blog blog1 = Blog.ofNewBlog(
                "marco1",
                true,
                "marco's blog",
                "marco",
                "Spring Blog!"
        );

        //TODO#3-7 member,blog, role 연결
        BlogMemberMapping blogMemberMapping = BlogMemberMapping.ofNewBlogMemberMapping(
                member,
                blog,
                role
        );

        BlogMemberMapping blogMemberMapping1 = BlogMemberMapping.ofNewBlogMemberMapping(
                member1,
                blog1,
                role
        );

        blog.addBlogMemberMapping(blogMemberMapping);
        blog1.addBlogMemberMapping(blogMemberMapping1);
        blogRepository.save(blog);
        blogRepository.save(blog1);

        //TODO#3-8 블로그 카테고리 저장
        Category category = Category.ofNewRootCategory(
                blog,
                topic,
                "spring-data-jpa",
                1
        );
        Category category1 = Category.ofNewRootCategory(
                blog1,
                topic,
                "winter-data-jpa",
                2
        );
        Category category2 = Category.ofNewRootCategory(
                blog1,
                topic,
                "fall-data-jpa",
                1
        );
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category);

        log.error("mbNo1 : {}", memberRepository.findById(1L));
    }
}
