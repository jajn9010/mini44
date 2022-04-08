package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private List<CommentResponseDto> comments;

    public PostResponseDto (Post post, List<CommentResponseDto> commentList) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.imageUrl = post.getImageUrl();
        this.comments = commentList;
    }
}
