package com.nhnacademy.frontend.category.service;

import com.nhnacademy.frontend.category.dto.CategoryRegisterRequest;
import com.nhnacademy.frontend.category.dto.CategoryResponse;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface CategoryService {

    CategoryResponse registerCategory(@Validated CategoryRegisterRequest categoryRegisterRequest);

    CategoryResponse getCategory(Long categoryId);

    List<CategoryResponse> getRootCategoryList(String blogFid);

    List<CategoryResponse> getSubCategoryList(String blogFid, Long categoryId);

    void deleteCategory(String blogFid, Long categoryId);
}
