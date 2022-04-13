package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;



@Setter
@Getter
@RequiredArgsConstructor
public class PostResponseDto {

    private Long postId;
    private String title;
    private String content;
    private String location;
    private String imageUrl;
//    private String nickname;
    private LocalDateTime createdAt;
    private List<Comment> comments;

    public PostResponseDto(Long postId, String title, String content, String location, String imageUrl,LocalDateTime createdAt, List<Comment> comments) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.location = location;
        this.imageUrl = imageUrl;
//        this.nickname = nickname;
        this.createdAt = this.createdAt;
        this.comments = comments;
    }
}
