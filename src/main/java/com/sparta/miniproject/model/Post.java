package com.sparta.miniproject.model;

import com.sparta.miniproject.dto.PostRequestDto;
import com.sparta.miniproject.dto.PostResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String location;

    @Column(columnDefinition = "mediumblob")
    private String imageUrl;

    @ManyToOne
    private User user;

    @OneToMany(fetch = FetchType.EAGER ,cascade =CascadeType.ALL)
    @JoinColumn
    List<Comment> comments;

    public Post(User user,PostRequestDto postRequestDto) {
        this.user = user;
        this.content = postRequestDto.getContent();
        this.imageUrl = postRequestDto.getImageUrl();
        this.title = postRequestDto.getTitle();
        this.comments = postRequestDto.getComments();
    }

    public Post(PostResponseDto responseDto){
        this.title = responseDto.getTitle();
        this.content = responseDto.getContent();
        this.location = responseDto.getLocation();
        this.imageUrl = responseDto.getImageUrl();
        this.user = user;

    }


    public void updatePost(PostRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
        this.imageUrl = postRequestDto.getImageUrl();
    }

}
