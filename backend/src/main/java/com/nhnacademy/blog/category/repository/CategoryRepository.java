package com.nhnacademy.blog.category.repository;

import com.nhnacademy.blog.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByCategoryName(String categoryName);

    List<Category> findAllByBlog_BlogFidAndParentCategoryIsNull(String blogBlogFid);

    List<Category> findByBlog_blogFidAndParentCategory_CategoryId(String blogBlogFid, Long parentCategoryCategoryId);
}
