package com.sparta.miniproject.model;

import com.sparta.miniproject.dto.CommentRequestDto;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String comment;

    @Column
    private Long likeCount;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    public Comment (User user, Post post, String comment) {
    this.user = user;
    this.post = post;
    this.comment = comment;
    this.likeCount = 0L;
    }
}
