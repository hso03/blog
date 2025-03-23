package com.nhnacademy.frontend.controller;

import com.nhnacademy.frontend.blog.adaptor.BlogAdaptor;
import com.nhnacademy.frontend.post.adptor.PostAdaptor;
import com.nhnacademy.frontend.post.dto.PostResponse;
import com.nhnacademy.frontend.security.userdetail.MemberDetails;
import com.nhnacademy.frontend.topic.dto.TopicResponse;
import com.nhnacademy.frontend.topic.service.TopicService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Slf4j
@Controller
@RequestMapping
@AllArgsConstructor
public class IndexController {

    private final TopicService topicService;
    private final PostAdaptor postAdaptor;
    private final BlogAdaptor blogAdaptor;

    @GetMapping(value = {"/", "index.do"})
    public String getIndex(
            Model model,
            @RequestParam(value = "topic_id", required = false) Integer topicId,
            @RequestParam(value = "topic_pid", required = false) Integer topicPid,
            @AuthenticationPrincipal MemberDetails memberDetails
    ){
        List<PostResponse> postResponseList = postAdaptor.getPostListAll().getBody();

        if(Objects.nonNull(memberDetails)){
            String blogFid = blogAdaptor.getMainBlogFid(memberDetails.getMemberResponse().getMbNo()).getBody();
            model.addAttribute("blogFid",blogFid);
        }
        model.addAttribute("postResponseList", postResponseList);
        model.addAttribute("topicId", topicId);
        model.addAttribute("topicPid", topicPid);

        return "index/index";
    }

    @ModelAttribute("rootTopics")
    public List<TopicResponse> rootTopics() {
        return topicService.rootTopicList();
    }

    @ModelAttribute("subTopics")
    public List<TopicResponse> subTopics(@RequestParam(value = "topic_id", required = false) Integer topicId){
        if(Objects.isNull(topicId)) {
            return Collections.emptyList();
        }
        return topicService.subTopicList(topicId);
    }

//    @ModelAttribute("subTopicPostList")
//    public List<PostResponse> subTopicPosts(){
//
//
//        return null;
//    }
}