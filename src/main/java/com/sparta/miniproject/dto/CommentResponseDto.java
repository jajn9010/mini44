package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.model.Post;
import com.sparta.miniproject.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private String comment;
    private User user;
    private Post post;
    private Long likeCount;

    public CommentResponseDto(Comment comment) {
        this.comment = comment.getComment();
        this.user = comment.getUser();
        this.post = comment.getPost();
        this.likeCount = comment.getLikeCount();
    }
}
