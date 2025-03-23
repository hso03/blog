package com.nhnacademy.blog.web.Category;

import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.category.dto.CategoryResponse;
import com.nhnacademy.blog.category.dto.CategoryRegisterRequest;
import com.nhnacademy.blog.category.repository.CategoryRepository;
import com.nhnacademy.blog.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;

    @PostMapping
    public ResponseEntity<CategoryResponse> postCategory(@Validated @RequestBody CategoryRegisterRequest categoryRegisterRequest){
        CategoryResponse categoryResponse = categoryService.registerCategory(categoryRegisterRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryResponse);
    }

    @GetMapping("/{category-id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable("category-id")Long categoryId){
        CategoryResponse categoryResponse = categoryService.getCategory(categoryId);
        return ResponseEntity
                .ok(categoryResponse);
    }

    @GetMapping("/list/{blog-fid}")
    public ResponseEntity<List<CategoryResponse>> getRootCategoryList(@PathVariable("blog-fid") String blogFid){
        List<CategoryResponse> categoryResponseList = categoryService.getRootCategoryList(blogFid);

        return ResponseEntity.
                ok(categoryResponseList);
    }

    @GetMapping("/list/{category-id}/{blog-fid}")
    public ResponseEntity<List<CategoryResponse>> getSubCategoryList(
            @PathVariable("blog-fid") String blogFid,
            @PathVariable("category-id") Long categoryId
    ){
        List<CategoryResponse> categoryResponseList = categoryService.getSubCategoryList(blogFid, categoryId);

        return ResponseEntity.ok(categoryResponseList);
    }



    @DeleteMapping("/{category-id}/{blog-fid}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("blog-fid") String blogFid, @PathVariable("category-id")Long categoryId){

        categoryService.deleteCategory(blogFid, categoryId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}