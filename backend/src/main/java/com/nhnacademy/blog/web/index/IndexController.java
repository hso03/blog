package com.nhnacademy.blog.web.index;

import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.bloginfo.repository.CustomBlogRepository;
import com.nhnacademy.blog.bloginfo.repository.impl.CustomBlogRepositoryImpl;
import com.nhnacademy.blog.blogmember.repository.BlogMemberMappingRepository;
import com.nhnacademy.blog.common.exception.NotFoundException;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.dto.MemberResponse;
import com.nhnacademy.blog.member.service.MemberService;
import com.nhnacademy.blog.topic.dto.TopicResponse;
import com.nhnacademy.blog.topic.service.TopicService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpRequest;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
/**
 * - TODO#9 구현 2순위 - 블로그의 Index 페이지를 구현 합니다.
 */

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexController {
    private final TopicService topicService;
    private final BlogRepository blogRepository;

    @ModelAttribute("rootTopics")
    public List<TopicResponse> rootTopics() {
        List<TopicResponse> rootTopics =  topicService.getRootTopics();
        return rootTopics;
    }

    @ModelAttribute("subTopics")
    public List<TopicResponse> subTopics(@RequestParam(value = "topic_id", required = false) Integer topicId) {
        if(Objects.isNull(topicId)) {
            return Collections.emptyList();
        }
        List<TopicResponse> subTopics =  topicService.getSubTopics(topicId);
        log.debug("subTopics: {}", subTopics);
        return subTopics;
    }

    @GetMapping(value = {"/","/index.do"})
    public String index(
            Model model,
            @RequestParam(value = "topic_id", required = false) Integer topicId,
            @RequestParam(value = "topic_pid", required = false) Integer topicPid,
            HttpSession session
    ) {
        log.debug("mbNo : {}", session.getAttribute("mbNo"));
        Long mbNo = (Long) session.getAttribute("mbNo");
        if(Objects.nonNull(mbNo)){
            String blogFid = blogRepository.findBlogFidFromMainBlog(mbNo);
            model.addAttribute("blogFid", blogFid);
        }
        model.addAttribute("topicId", topicId);
        model.addAttribute("topicPid", topicPid);

        return "index/index";
    }

}