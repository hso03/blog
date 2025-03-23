package com.nhnacademy.blog.post.service.Impl;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.bloginfo.repository.BlogRepository;
import com.nhnacademy.blog.category.domain.Category;
import com.nhnacademy.blog.category.dto.CategoryResponse;
import com.nhnacademy.blog.category.repository.CategoryRepository;
import com.nhnacademy.blog.common.exception.NotFoundException;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.member.repository.MemberRepository;
import com.nhnacademy.blog.post.domain.Post;
import com.nhnacademy.blog.post.dto.PostRegisterRequest;
import com.nhnacademy.blog.post.dto.PostResponse;
import com.nhnacademy.blog.post.repository.PostRepository;
import com.nhnacademy.blog.post.service.PostService;
import com.nhnacademy.blog.postcategory.domain.PostCategoryMapping;
import com.nhnacademy.blog.postcategory.repository.PostCategoryRepository;
import groovy.util.ObservableList;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final BlogRepository blogRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final PostCategoryRepository postCategoryRepository;

    @Override
    public PostResponse registerPost(PostRegisterRequest postRegisterRequest, String blogFid, Long mbNo) {

        Blog blog = blogRepository.findByBlogFid(blogFid).orElseThrow(()-> new NotFoundException("blog not found"));

        Member member = memberRepository.findByMbNo(mbNo).orElseThrow(()->new NotFoundException("member not found"));

        Post post = Post.ofNewPost(postRegisterRequest, blog, member);

        Post savedPost = postRepository.save(post);

        PostCategoryMapping postCategoryMapping = PostCategoryMapping.ofNewPostCategoryMapping(
                savedPost,
                categoryRepository.findById(postRegisterRequest.getCategoryId()).orElseThrow(()->new NotFoundException("category id 찾을수 없습니다."))
        );

        PostCategoryMapping savedPostCategoryMapping = postCategoryRepository.save(postCategoryMapping);

        return new PostResponse(
                post.getPostId(),
                post.getBlog().getBlogFid(),
                savedPostCategoryMapping.getCategory().getParentCategory().getCategoryName(),
                savedPostCategoryMapping.getCategory().getCategoryName(),
                savedPostCategoryMapping.getCategory().getTopic().getTopicName(),
                post.getCreatedMember().getMbNo(),
                Objects.isNull(post.getUpdatedMember())?null:post.getUpdatedMember().getMbNo(),
                post.getPostTitle(),
                post.getPostContent(),
                post.getPostIsPublic(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    @Override
    public List<PostResponse> getListPostByBlogFid(String blogFid) {

        Blog blog = blogRepository.findByBlogFid(blogFid).orElseThrow(()-> new NotFoundException("not found"));

        List<Post> listPost = postRepository.findAllByBlog_BlogIdOrderByCreatedAtDesc(blog.getBlogId());

        List<PostResponse> listPostResponse = new ArrayList<>();

        for(Post post : listPost){

            PostCategoryMapping findPostCategory = postCategoryRepository.findPostCategoryMappingsByPost(post).orElseThrow(()->new NotFoundException("not found"));

            listPostResponse.add(new PostResponse(
                    post.getPostId(),
                    post.getBlog().getBlogFid(),
                    findPostCategory.getCategory().getParentCategory().getCategoryName(),
                    findPostCategory.getCategory().getCategoryName(),
                    findPostCategory.getCategory().getTopic().getTopicName(),
                    post.getCreatedMember().getMbNo(),
                    Objects.isNull(post.getUpdatedMember())?null : post.getUpdatedMember().getMbNo(),
                    post.getPostTitle(),
                    post.getPostContent(),
                    post.getPostIsPublic(),
                    post.getCreatedAt(),
                    post.getUpdatedAt()
            ));
        }

        return listPostResponse;
    }

    @Override
    public List<PostResponse> getListPostByAll() {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();

        List<PostResponse> postResponseList = new ArrayList<>();
        for(Post post : postList){
            PostCategoryMapping findPostCategory = postCategoryRepository.findPostCategoryMappingsByPost(post).orElseThrow(()->new NotFoundException("not found"));
            postResponseList.add(new PostResponse(
                    post.getPostId(),
                    post.getBlog().getBlogFid(),
                    findPostCategory.getCategory().getParentCategory().getCategoryName(),
                    findPostCategory.getCategory().getCategoryName(),
                    findPostCategory.getCategory().getTopic().getTopicName(),
                    post.getCreatedMember().getMbNo(),
                    Objects.isNull(post.getUpdatedMember())?null : post.getUpdatedMember().getMbNo(),
                    post.getPostTitle(),
                    post.getPostContent(),
                    post.getPostIsPublic(),
                    post.getCreatedAt(),
                    post.getUpdatedAt()
            ));
        }

        return postResponseList;
    }

}
