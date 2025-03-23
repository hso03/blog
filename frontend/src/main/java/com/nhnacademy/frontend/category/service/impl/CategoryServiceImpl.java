package com.nhnacademy.frontend.category.service.impl;


import com.nhnacademy.frontend.category.adpator.CategoryAdaptor;
import com.nhnacademy.frontend.category.dto.CategoryRegisterRequest;
import com.nhnacademy.frontend.category.dto.CategoryResponse;
import com.nhnacademy.frontend.category.service.CategoryService;
import com.nhnacademy.frontend.common.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryAdaptor categoryAdaptor;

    @Override
    public CategoryResponse registerCategory(CategoryRegisterRequest categoryRegisterRequest) {
        ResponseEntity<CategoryResponse> categoryResponseEntity = categoryAdaptor.postCategory(categoryRegisterRequest);
        if(!categoryResponseEntity.getStatusCode().is2xxSuccessful()){
            throw new ErrorException("not 2xx status code");
        }

        return categoryResponseEntity.getBody();
    }

    @Override
    public CategoryResponse getCategory(Long categoryId) {
        ResponseEntity<CategoryResponse> categoryResponseEntity = categoryAdaptor.getCategory(categoryId);
        if(!categoryResponseEntity.getStatusCode().is2xxSuccessful()){
            throw new ErrorException("not 2xx status code");
        }
        return categoryResponseEntity.getBody();
    }

    @Override
    public List<CategoryResponse> getRootCategoryList(String blogFid) {
        ResponseEntity<List<CategoryResponse>> categoryListEntity = categoryAdaptor.getRootCategoryList(blogFid);
        if(!categoryListEntity.getStatusCode().is2xxSuccessful()){
            throw new ErrorException("not 2xx status code");
        }

        return categoryListEntity.getBody();
    }

    @Override
    public List<CategoryResponse> getSubCategoryList(String blogFid, Long categoryId) {
        ResponseEntity<List<CategoryResponse>> categoryListEntity =categoryAdaptor.getSubCategoryList(blogFid, categoryId);
        if(!categoryListEntity.getStatusCode().is2xxSuccessful()){
            throw new ErrorException("not 2xx status code");
        }

        return categoryListEntity.getBody();
    }

    @Override
    public void deleteCategory(String blogFid, Long categoryId) {
        ResponseEntity<Void> deleteEntity = categoryAdaptor.deleteCategory(blogFid, categoryId);
        if(!deleteEntity.getStatusCode().is2xxSuccessful()){
            throw new ErrorException("not 2xx status code");
        }
    }
}
