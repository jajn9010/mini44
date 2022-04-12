package com.sparta.miniproject.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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
    private String nickName;
    private List<CommentResponseDto> comments;

    public PostResponseDto(Long postId, String title, String content, String location, String imageUrl, String nickName, List<CommentResponseDto> comments) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.location = location;
        this.imageUrl = imageUrl;
        this.nickName =  nickName;
        this.comments = comments;
    }

}
