package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.Post;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.Column;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    private Long id;
    private String title;
    private String content;
    private String location;
    private String imageUrl;
    private String nickname;
    private String user;



}
