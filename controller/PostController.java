package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.PostRequestDto;
import com.sparta.miniproject.dto.PostResponseDto;
import com.sparta.miniproject.repository.PostRepository;
import com.sparta.miniproject.security.UserDetailsImpl;
import com.sparta.miniproject.service.PostService;
import com.sparta.miniproject.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    private final S3Uploader s3Uploader;
    private final PostRepository postRepository;

    //게시판 작성
//    public Post createPost(@RequestBody PostResponseDto postResponseDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//      if (userDetails == null) {
//            throw new IllegalArgumentException("로그인을 해야 게시글을 작성하실 수 있습니다.");
//        }
//        Post post = postService.savePost(postResponseDto);
//        return post;
//    }

    @PostMapping("/posts")
    public void createPost(@RequestParam ("title") String title,
                                      @RequestParam ("content") String content,
                                      @RequestParam ("location") String location,
                                      @RequestParam ("file") MultipartFile imageUrl,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails
                ) throws Exception {

        PostRequestDto postRequestDto = new PostRequestDto(title,content,location,imageUrl,userDetails.getUser());

        postService.createPost(postRequestDto,"static");

    }

//    @PostMapping("/images")
//    public String uploadImage(@RequestPart MultipartFile file) {
//        return fileUploadService.uploadImage(file);
//    }

    //게시판 삭제
    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        if (userDetails == null) {
//            throw new IllegalArgumentException("게시글을 삭제하실 권한이 없습니다.");
//        }
        postService.deletePost(postId, userDetails);
    }

    //    이미지 업로드
    @GetMapping("/posts/{postId}")
    public PostResponseDto findPost (@PathVariable Long postId){
        return postService.findPost(postId);
    }

    @PutMapping("/posts/{postId}")
    public String updatePost (@RequestBody PostRequestDto postRequestDto, @PathVariable Long postId,  UserDetailsImpl userDetails){
        return postService.updatePost(postRequestDto, postId, userDetails);
    }
}
