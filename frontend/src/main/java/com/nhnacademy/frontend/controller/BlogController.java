package com.nhnacademy.frontend.controller;

import com.nhnacademy.frontend.blog.adaptor.BlogAdaptor;
import com.nhnacademy.frontend.blog.dto.BlogResponse;
import com.nhnacademy.frontend.blog.dto.BlogUpdateRequest;
import com.nhnacademy.frontend.post.adptor.PostAdaptor;
import com.nhnacademy.frontend.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/blog/{blog-fid}")
@RequiredArgsConstructor
public class BlogController {

    // service 사용하는걸 잊고 adaptor로 함.. ㅠ
    private final BlogAdaptor blogAdaptor;
    private final PostAdaptor postAdaptor;

    @GetMapping(value = {"","/","/index.do"})
    public String getBlog(Model model, @PathVariable("blog-fid") String blogFid){
        BlogResponse blogResponse = blogAdaptor.getBlog(blogFid).getBody();
        List<PostResponse> postResponseList = postAdaptor.getPost(blogFid).getBody();

        model.addAttribute("postResponseList", postResponseList);
        model.addAttribute("blogResponse",blogResponse);
        return "blog/index";
    }

    // GET 블로그 관리
    @GetMapping(value = {"/manage"})
    public String getManage(Model model, @PathVariable("blog-fid") String blogFid){
        BlogResponse blogResponse = blogAdaptor.getBlog(blogFid).getBody();

        model.addAttribute("blogResponse",blogResponse);
        return "blog/manage";
    }

    @PostMapping(value = "/manageAction")
    public String postManage(Model model, @PathVariable("blog-fid") String blogFid, BlogUpdateRequest blogUpdateRequest){
        BlogResponse blogResponse = blogAdaptor.getBlog(blogFid).getBody();
        log.error("updateBlog : {}", blogUpdateRequest);
        BlogResponse updateBlog = blogAdaptor.putBlog(blogFid, blogUpdateRequest).getBody();
        model.addAttribute("blogResponse",blogResponse);
        return "redirect:/blog/%s".formatted(blogFid);
    }
}
