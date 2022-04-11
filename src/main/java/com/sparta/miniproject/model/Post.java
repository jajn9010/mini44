package com.sparta.miniproject.model;

import com.sparta.miniproject.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Comment> comments;

    public void updatePost(PostRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
        this.imageUrl = postRequestDto.getImageUrl();
    }
}
