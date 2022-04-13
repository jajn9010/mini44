package com.sparta.miniproject.dto;

import com.sparta.miniproject.model.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto {

    private String imageUrl;
    private String fileName;
    private String title;
    private String content;
    private String location;
    private User user;

    public ImageDto( String fileName,String imageUrl) {
        this.imageUrl = imageUrl;
        this.fileName = fileName;
    }
}