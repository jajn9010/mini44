package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.PostRequestDto;
import com.sparta.miniproject.dto.PostResponseDto;
import com.sparta.miniproject.model.Post;
import com.sparta.miniproject.security.UserDetailsImpl;
import com.sparta.miniproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    @GetMapping("/api/posts/{postId}")
    public PostResponseDto findPost(@PathVariable Long postId) {
        return postService.findPost(postId);
    }

    @PutMapping("/api/posts/{postId}")
    public String updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        return postService.updatePost(postRequestDto, postId, userDetails);
    }

    @DeleteMapping("/api/posts/{postId}")
    public String deletePost(@PathVariable Long postId, UserDetailsImpl userDetails) {
        return postService.deletePost(postId, userDetails);
    }
}
