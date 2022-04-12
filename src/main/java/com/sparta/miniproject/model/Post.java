package com.sparta.miniproject.model;

import com.sparta.miniproject.dto.PostRequestDto;
import com.sparta.miniproject.dto.PostResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @OneToMany(cascade =CascadeType.ALL)
    @JoinColumn(name = "postId")
    List<Comment> comments = new ArrayList<>();

    public Post(Long postId, String title, String location, String imageUrl, User user) {
        this.postId = postId;
        this.title = title;
        this.location = location;
        this.imageUrl = imageUrl;
        this.user = user;
    }


    public Post(PostResponseDto responseDto,User user){
        this.title = responseDto.getTitle();
        this.content = responseDto.getContent();
        this.location = responseDto.getLocation();
        this.imageUrl = responseDto.getImageUrl();
        this.nickname = user.getNickname();
        this.user = user;

    }


    public void updatePost(PostRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
        this.imageUrl = postRequestDto.getImageUrl();
    }

}
