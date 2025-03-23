package com.nhnacademy.frontend.controller;

import com.nhnacademy.frontend.category.dto.CategoryRegisterRequest;
import com.nhnacademy.frontend.category.dto.CategoryResponse;
import com.nhnacademy.frontend.category.service.CategoryService;
import com.nhnacademy.frontend.topic.dto.TopicResponse;
import com.nhnacademy.frontend.topic.service.TopicService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/blog/{blog-fid}/category")
@RequiredArgsConstructor
public class CategoryController {

    private final TopicService topicService;
    private final CategoryService categoryService;

    @ModelAttribute("rootTopics")
    public List<TopicResponse> rootTopics() {
        return topicService.rootTopicList();
    }

    @ModelAttribute("rootCategories")
    public List<CategoryResponse> rootCategory(@PathVariable("blog-fid") String blogFid){
        return categoryService.getRootCategoryList(blogFid);
    }

    @GetMapping("/subTopics")
    @ResponseBody
    public List<TopicResponse> subTopics(@RequestParam(value = "topic_id", required = false) Integer topicId){
        if(Objects.isNull(topicId)) {
            return Collections.emptyList();
        }
        return topicService.subTopicList(topicId);
    }

    @GetMapping("/{category-id}")
    @ResponseBody
    public List<CategoryResponse> subCategories(
            @PathVariable("blog-fid") String blogFid,
            @PathVariable("category-id") Long categoryId
    ){
        return categoryService.getSubCategoryList(blogFid, categoryId);
    }

    //-----------------------------------------------------------------------------//

    @GetMapping
    public String getCategory(Model model, @PathVariable("blog-fid") String blogFid, HttpServletResponse response){
        model.addAttribute("blogFid", blogFid);
        return "blog/category";
    }

    // RootCategory Register Page
    @GetMapping("/rootcategory")
    public String getRootCategory(Model model, @PathVariable("blog-fid") String blogFid){

        model.addAttribute("blogFid", blogFid);
        return "blog/rootcategory";
    }

    // SubCategory Register Page
    @GetMapping("/subcategory")
    public String getSubCategory(Model model, @PathVariable("blog-fid") String blogFid, @RequestParam("categoryId") Long categoryId ){
        CategoryResponse categoryResponse = categoryService.getCategory(categoryId);

        model.addAttribute("categoryResponse", categoryResponse);
        model.addAttribute("blogFid", blogFid);
        return "blog/subcategory";
    }

    // After choosing rootCategory, redirect to the subCategory page
    @PostMapping("/categoryAction")
    public String postCategoryAction(@PathVariable("blog-fid") String blogFid, @RequestParam("categoryId") Long categoryId, RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("categoryId", categoryId);
        return "redirect:/blog/%s/category/subcategory".formatted(blogFid);
    }

    // After writing the rootCategory page, redirect to the category page
    @PostMapping("/rootcategory/postsAction")
    public String postCategory(@PathVariable("blog-fid") String blogFid, @Validated @ModelAttribute CategoryRegisterRequest categoryRegisterRequest){

        log.error("categoryResponse : {}",categoryRegisterRequest);
        CategoryResponse categoryResponse = categoryService.registerCategory(categoryRegisterRequest);

        return "redirect:/blog/%s/category".formatted(blogFid);
    }

    @PostMapping("/{category-id}")
    public String deleteCategory(@PathVariable("blog-fid")String blogFid, @PathVariable("category-id")Long categoryId, HttpServletResponse response){
        categoryService.deleteCategory(blogFid, categoryId);
        return "redirect:/blog/%s/category".formatted(blogFid);
    }

    @PostMapping("/subCategoryAction")
    public String subCategory(@PathVariable("blog-fid")String blogFid, @Validated @ModelAttribute CategoryRegisterRequest categoryRegisterRequest){
        CategoryResponse categoryResponse = categoryService.registerCategory(categoryRegisterRequest);
        log.error("subCategory : {}", categoryResponse);

        return "redirect:/blog/%s/category".formatted(blogFid);
    }

}
