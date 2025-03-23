package com.nhnacademy.frontend.controller;

import com.nhnacademy.frontend.category.adpator.CategoryAdaptor;
import com.nhnacademy.frontend.category.dto.CategoryResponse;
import com.nhnacademy.frontend.category.service.CategoryService;
import com.nhnacademy.frontend.post.adptor.PostAdaptor;
import com.nhnacademy.frontend.post.dto.PostRegisterRequest;
import com.nhnacademy.frontend.post.dto.PostResponse;
import com.nhnacademy.frontend.security.userdetail.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/blog/{blog-fid}")
@RequiredArgsConstructor
public class PostController {

    private final PostAdaptor postAdaptor;
    private final CategoryService categoryService;

    @ModelAttribute("rootCategories")
    public List<CategoryResponse> rootCategory(@PathVariable("blog-fid") String blogFid){
        return categoryService.getRootCategoryList(blogFid);
    }

    @GetMapping("/posts")
    public String getPost(Model model, @PathVariable("blog-fid") String blogFid){
        model.addAttribute("blogFid", blogFid);
        return "blog/posts";
    }

    @PostMapping("/postsAction")
    public String postPost(@PathVariable("blog-fid") String blogFid, PostRegisterRequest postRegisterRequest, @AuthenticationPrincipal MemberDetails memberDetails){
        Long mbNo = memberDetails.getMemberResponse().getMbNo();
        log.error("PostRegisterRequest : {}",postRegisterRequest);
        PostResponse postResponse = postAdaptor.registerPost(postRegisterRequest, blogFid, mbNo).getBody();
        log.error("PostResponse : {}", postResponse);
        return "redirect:/blog/%s".formatted(blogFid);
    }

}
