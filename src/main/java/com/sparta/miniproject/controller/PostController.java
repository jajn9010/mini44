package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.ImageDto;
import com.sparta.miniproject.dto.PostRequestDto;
import com.sparta.miniproject.dto.PostResponseDto;
import com.sparta.miniproject.model.Post;
import com.sparta.miniproject.security.UserDetailsImpl;
import com.sparta.miniproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    //게시판 작성
    @PostMapping("/posts")
    public void createPost(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("location") String location,
                           @RequestParam("file") MultipartFile imageUrl,
                           @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws Exception {

        PostRequestDto postRequestDto = new PostRequestDto(title, content, location, imageUrl, userDetails.getUser());

        postService.createPost(postRequestDto, "static");

    }

    //게시판 삭제
    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails);
    }

    //게시판 리스트
    @GetMapping("/posts/{postId}")
    public PostResponseDto findPost(@PathVariable Long postId) {
        return postService.findPost(postId);
    }

    //게시글 수정
    @PutMapping("/posts/{postId}")
    public void updatePost(@RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("location") String location,
                           @RequestParam("file") MultipartFile imageUrl,
                           @PathVariable Long postId,
                           @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws Exception {
        PostRequestDto postRequestDto = new PostRequestDto(title, content, location, imageUrl);
        postService.updatePost(postRequestDto, "static", postId, userDetails);
    }

}

