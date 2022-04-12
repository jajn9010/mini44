package com.sparta.miniproject.service;

import com.sparta.miniproject.dto.PostRequestDto;
import com.sparta.miniproject.dto.PostResponseDto;
import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.model.Post;
import com.sparta.miniproject.repository.CommentRepository;
import com.sparta.miniproject.repository.PostRepository;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto findPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        String title = post.getTitle();
        String content = post.getContent();
        String location = post.getLocation();
        String nickname = post.getUser().getNickName();
        String imageUrl = post.getImageUrl();
        LocalDateTime createdAt = post.getCreatedAt();
        List<Comment> comments = post.getComments();

        return new PostResponseDto(postId, title, content, location, nickname, imageUrl, createdAt, comments);
    }

    @Transactional
    public String updatePost(PostRequestDto postRequestDto, Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if(post.getUserId().equals(userDetails.getUser().getUserId())) {
            post.updatePost(postRequestDto);
        }
        return "게시글 수정 완료";
    }

    public String deletePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다.")
        );
        if(post.getUserId().equals(userDetails.getUser().getUserId())) {
            postRepository.deleteById(postId);
        }
        return "게시글 삭제 완료";
    }
}