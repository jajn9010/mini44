package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.CommentRequestDto;
import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.repository.CommentRepository;
import com.sparta.miniproject.security.UserDetailsImpl;
import com.sparta.miniproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/api/comments/{postId}")
    public Comment createComment(@PathVariable Long postId, @RequestBody CommentRequestDto requestDto){

    }

    @DeleteMapping("/api/comments/{postId}/{commentId}")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId, UserDetailsImpl userDetails) {
        commentService.deleteComment(postId, commentId, userDetails.getUser().getUserName);
        return "댓글 삭제 완료!";
    }
}
