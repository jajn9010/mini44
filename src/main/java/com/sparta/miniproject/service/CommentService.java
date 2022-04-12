package com.sparta.miniproject.service;

import com.sparta.miniproject.dto.CommentRequestDto;
import com.sparta.miniproject.dto.CommentResponseDto;
import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.model.Post;
import com.sparta.miniproject.model.User;
import com.sparta.miniproject.repository.CommentRepository;
import com.sparta.miniproject.repository.PostRepository;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public void createComment(Long postId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        String comm = commentRequestDto.getComment();
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        Comment comment = new Comment(user, post, comm);
        commentRepository.save(comment);
    }

    public String deleteComment(Long commentId, UserDetailsImpl userDetails) {
        com.sparta.miniproject.model.Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("삭제할 댓글이 없습니다.")
        );
//        if (comment.getUser().equals(userDetails.getUser().getUserId())) {
            commentRepository.deleteById(commentId);
//        }
    return "댓글 삭제 완료";}
}
