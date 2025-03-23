package com.nhnacademy.blog.postcategory.domain;

import com.nhnacademy.blog.category.domain.Category;
import com.nhnacademy.blog.post.domain.Post;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "post_category_mappings"
)
public class PostCategoryMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_category_id")
    private Long postCategoryId;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    private Category category;

    public PostCategoryMapping(){}

    private PostCategoryMapping(Post post, Category category) {
        this.post = post;
        this.category = category;
    }

    public static PostCategoryMapping ofNewPostCategoryMapping(Post post, Category category){
        return new PostCategoryMapping(post, category);
    }

    public Long getPostCategoryId() {
        return postCategoryId;
    }

    public Post getPost() {
        return post;
    }

    public Category getCategory() {
        return category;
    }
}
