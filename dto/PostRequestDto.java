package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String content;
    private String location;
    private MultipartFile imageUrl;
    private List<Comment> comments;
    private User user;


    public PostRequestDto(String title, String content, String location, MultipartFile imageUrl,User user) {
        this.title = title;
        this.content = content;
        this.location = location;
        this.imageUrl = imageUrl;
        this.user = user;
    }
}
