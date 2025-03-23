package com.nhnacademy.blog.category.service.impl;

import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.category.domain.Category;
import com.nhnacademy.blog.category.dto.CategoryRegisterRequest;
import com.nhnacademy.blog.category.dto.CategoryResponse;
import com.nhnacademy.blog.category.repository.CategoryRepository;
import com.nhnacademy.blog.category.service.CategoryService;
import com.nhnacademy.blog.common.exception.ConflictException;
import com.nhnacademy.blog.common.exception.NotFoundException;
import com.nhnacademy.blog.topic.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final BlogRepository blogRepository;
    private final TopicRepository topicRepository;

    @Override
    public CategoryResponse registerCategory(CategoryRegisterRequest categoryRegisterRequest) {
        if(categoryRepository.existsByCategoryName(categoryRegisterRequest.getCategoryName())){
            throw new ConflictException("중복된 카테고리 이름 입니다.");
        }

        if(Objects.nonNull(categoryRegisterRequest.getCategoryPid())) {
            Category category = Category.ofNewSubCategory(
                    categoryRepository.findById(categoryRegisterRequest.getCategoryPid()).orElseThrow(()->new NotFoundException("카테고리Pid를 찾을 수 없습니다.")),
                    blogRepository.findByBlogFid(categoryRegisterRequest.getBlogFid()).orElseThrow(() -> new NotFoundException("블로그를 찾을수 없습니다.")),
                    topicRepository.findById(categoryRegisterRequest.getSubTopicId()).orElseThrow(() -> new NotFoundException("토픽을 찾을수 없습니다.")),
                    categoryRegisterRequest.getCategoryName(),
                    categoryRegisterRequest.getCategorySec()
            );

            Category savedCategory = categoryRepository.save(category);

            return convertToResponse(savedCategory);

        }else{
            Category category = Category.ofNewRootCategory(
                    blogRepository.findByBlogFid(categoryRegisterRequest.getBlogFid()).orElseThrow(() -> new NotFoundException("블로그를 찾을수 없습니다.")),
                    topicRepository.findById(categoryRegisterRequest.getSubTopicId()).orElseThrow(() -> new NotFoundException("토픽을 찾을수 없습니다.")),
                    categoryRegisterRequest.getCategoryName(),
                    categoryRegisterRequest.getCategorySec()
            );

            Category savedCategory = categoryRepository.save(category);

            return convertToResponse(savedCategory);
        }
    }

    @Override
    public CategoryResponse getCategory(Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if(categoryOptional.isEmpty()){
            throw new NotFoundException("categoryId not found");
        }

        return convertToResponse(categoryOptional.get());
    }

    @Override
    public List<CategoryResponse> getRootCategoryList(String blogFid) {

        if(!blogRepository.existsByBlogFid(blogFid)){
            throw new NotFoundException("blogFid 찾을수 없습니다.");
        }

        List<Category> categoryList = categoryRepository.findAllByBlog_BlogFidAndParentCategoryIsNull(blogFid);

        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        for(Category category : categoryList){
            categoryResponseList.add(convertToResponse(category));
        }

        return categoryResponseList;
    }

    @Override
    public List<CategoryResponse> getSubCategoryList(String blogFid, Long categoryId) {

        if(!blogRepository.existsByBlogFid(blogFid)){
            throw new NotFoundException("blogFid 찾을수 없습니다.");
        }

        if(!categoryRepository.existsById(categoryId)){
            throw new NotFoundException("categoryId 찾을수 없습니다.");
        }

        List<Category> categoryList = categoryRepository.findByBlog_blogFidAndParentCategory_CategoryId(blogFid, categoryId);
        List<CategoryResponse> categoryResponseList = new ArrayList<>();

        for(Category category : categoryList){
            categoryResponseList.add(convertToResponse(category));
        }

        return categoryResponseList;
    }

    @Override
    public void deleteCategory(String blogFid, Long categoryId) {
        if(!blogRepository.existsByBlogFid(blogFid)){
            throw new NotFoundException("blogFid not found");
        }

        if(!categoryRepository.existsById(categoryId)){
            throw new NotFoundException("categoryId not found");
        }

        categoryRepository.deleteById(categoryId);
    }

    private CategoryResponse convertToResponse(Category category) {
        return new CategoryResponse(
                category.getCategoryId(),
                Objects.isNull(category.getParentCategory()) ? null : category.getParentCategory().getCategoryId(),
                category.getBlog().getBlogFid(),
                category.getTopic().getTopicId(),
                category.getCategoryName(),
                category.getCategorySec(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
