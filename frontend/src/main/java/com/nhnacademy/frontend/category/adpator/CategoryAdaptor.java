package com.nhnacademy.frontend.category.adpator;

import com.nhnacademy.frontend.category.dto.CategoryRegisterRequest;
import com.nhnacademy.frontend.category.dto.CategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "categoryAdaptor", url = "http://localhost:8080/api/v1/categories")
public interface CategoryAdaptor {

    @PostMapping
    ResponseEntity<CategoryResponse> postCategory(@Validated @RequestBody CategoryRegisterRequest CategoryRegisterRequest);

    @GetMapping("/{category-id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable("category-id")Long categoryId);

    @GetMapping("/list/{blog-fid}")
    ResponseEntity<List<CategoryResponse>> getRootCategoryList(@PathVariable("blog-fid") String blogFid);

    @GetMapping("/list/{category-id}/{blog-fid}")
    ResponseEntity<List<CategoryResponse>> getSubCategoryList(
            @PathVariable("blog-fid") String blogFid,
            @PathVariable("category-id") Long categoryId
    );

    @DeleteMapping("/{category-id}/{blog-fid}")
    ResponseEntity<Void> deleteCategory(@PathVariable("blog-fid") String blogFid, @PathVariable("category-id")Long categoryId);
}

