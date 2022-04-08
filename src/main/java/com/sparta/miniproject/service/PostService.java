package com.sparta.miniproject.service;

import com.sparta.miniproject.dto.CommentResponseDto;
import com.sparta.miniproject.dto.PostResponseDto;
import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.model.Post;
import com.sparta.miniproject.repository.CommentRepository;
import com.sparta.miniproject.repository.PostRepository;
import com.sparta.miniproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<PostResponseDto> findAllPost(Long postId) {
        List<PostResponseDto> postList = new ArrayList<>();

        List<Post> postsList = postRepository.findAll();
        for (Post post : postsList) {
            List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
            List<Comment> commentList = commentRepository.findAll();

            for (Comment comment : commentList) {
                CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
                commentResponseDtoList.add(commentResponseDto);
            }

            PostResponseDto postResponseDto = new PostResponseDto(post, commentResponseDtoList);
            postList.add(postResponseDto);
        }
        return postList;
    }
}