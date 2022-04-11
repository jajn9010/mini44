package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String location;
    private String nickname;
    private String imageUrl;
    private LocalDateTime createAt;
    private List<Comment> comments;
}
