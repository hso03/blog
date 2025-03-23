package com.nhnacademy.blog.post.domain;

import com.nhnacademy.blog.bloginfo.domain.Blog;
import com.nhnacademy.blog.member.domain.Member;
import com.nhnacademy.blog.post.dto.PostRegisterRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Cleanup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@Getter
@ToString
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false, referencedColumnName = "blog_id")
    private Blog blog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_mb_no", nullable = false, referencedColumnName = "mb_no")
    private Member createdMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_mb_no", nullable = true, referencedColumnName = "mb_no")
    private Member updatedMember;

    @Column(name = "post_title", nullable = false)
    private String postTitle;

    @Column(name = "post_content", nullable = true)
    private String postContent;

    @Column(name = "post_is_pulibc", nullable = false)
    private Boolean postIsPublic;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;

    private Post(Blog blog, Member createdMember, String postTitle, String postContent, Boolean postIsPublic) {
        this.blog = blog;
        this.createdMember = createdMember;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postIsPublic = postIsPublic;
    }

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    public static Post ofNewPost(PostRegisterRequest postRegisterRequest, Blog blog, Member createdMember){
        return new Post(
                blog,
                createdMember,
                postRegisterRequest.getPostTitle(),
                postRegisterRequest.getPostContent(),
                postRegisterRequest.getPostIsPublic()
        );
    }
}
