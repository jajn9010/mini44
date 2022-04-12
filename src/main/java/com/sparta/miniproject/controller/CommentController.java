package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.CommentRequestDto;
import com.sparta.miniproject.dto.CommentResponseDto;
import com.sparta.miniproject.security.UserDetailsImpl;
import com.sparta.miniproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Comment;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/comments/{postId}")
    public void createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto, UserDetailsImpl userDetails){
        commentService.createComment(postId, commentRequestDto, userDetails);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public String deleteComment(@PathVariable Long commentId,  UserDetailsImpl userDetails) {
        return commentService.deleteComment( commentId, userDetails);
    }
}
