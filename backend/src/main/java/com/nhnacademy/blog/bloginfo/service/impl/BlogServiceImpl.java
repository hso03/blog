package com.nhnacademy.blog.bloginfo.service.impl;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.bloginfo.dto.BlogRegisterRequest;
import com.nhnacademy.blog.bloginfo.dto.BlogResponse;
import com.nhnacademy.blog.bloginfo.dto.BlogUpdateRequest;
import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.bloginfo.service.BlogService;
import com.nhnacademy.blog.common.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Override
    @Transactional(readOnly = true)
    public BlogResponse getBlogByFid(String blogFid) {

        Optional<Blog> optionalBlog = blogRepository.findByBlogFid(blogFid);

        if(optionalBlog.isPresent()){
            Blog blog = optionalBlog.get();
            return new BlogResponse(
                    blog.getBlogId(),
                    blog.getBlogFid(),
                    blog.isBlogMain(),
                    blog.getBlogName(),
                    blog.getBlogMbNickname(),
                    blog.getBlogDescription(),
                    blog.getCreatedAt(),
                    blog.getUpdatedAt(),
                    blog.getBlogIsPublic()
            );
        }

        throw new NotFoundException("not found");
    }

    @Override
    public BlogResponse updateBlog(BlogUpdateRequest blogUpdateRequest) {

        log.error("service blogUpdateRequest : {}", blogUpdateRequest);

        //Fid 확인
        Optional<Blog> optionalBlog = blogRepository.findByBlogFid(blogUpdateRequest.getBlogFid());
        if(optionalBlog.isEmpty()){
            throw new NotFoundException("Fid Not Found");
        }

        Blog blog = optionalBlog.get();
        blog.update(
                blogUpdateRequest.getBlogName(),
                blogUpdateRequest.getBlogMbNickname(),
                blogUpdateRequest.getBlogDescription(),
                true,
                LocalDateTime.now()
        );

        log.error("service blog update : {}", blog);

        return new BlogResponse(
                blog.getBlogId(),
                blog.getBlogFid(),
                blog.isBlogMain(),
                blog.getBlogName(),
                blog.getBlogMbNickname(),
                blog.getBlogDescription(),
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                blog.getBlogIsPublic()
        );
    }

    @Override
    public BlogResponse createBlog(BlogRegisterRequest blogRegisterRequest) {

        Blog blog = Blog.ofNewBlog(
                blogRegisterRequest.getBlogFid(),
                blogRegisterRequest.isBlogMain(),
                blogRegisterRequest.getBlogName(),
                blogRegisterRequest.getBlogNickName(),
                blogRegisterRequest.getBlogDescription()
        );

        Blog savedBlog = blogRepository.save(blog);

        return new BlogResponse(
                savedBlog.getBlogId(),
                savedBlog.getBlogFid(),
                savedBlog.isBlogMain(),
                savedBlog.getBlogName(),
                savedBlog.getBlogMbNickname(),
                savedBlog.getBlogDescription(),
                savedBlog.getCreatedAt(),
                savedBlog.getUpdatedAt(),
                savedBlog.getBlogIsPublic()
        );
    }

    @Override
    public void deleteBlog(String blogFid) {
        if(!blogRepository.existsByBlogFid(blogFid)){
            throw new NotFoundException("%s blogFid is not found".formatted(blogFid));
        }

        blogRepository.deleteByBlogFid(blogFid);
    }
}