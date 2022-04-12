package com.sparta.miniproject.controller;

import com.sparta.miniproject.dto.PostResponseDto;
import com.sparta.miniproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @Autowired
    PostService postService;

//    @GetMapping("/posts/{page}")
//    public Map<String, Object> home(@PathVariable int page, @PageableDefault(page = 0,
//            size = 12, sort = "modifiedDt", direction = Sort.Direction.DESC) Pageable pageable) {
//        Pageable pageable1 = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());
//        Page<Post> posts = postService.home(pageable1);
//        Map<String, Object> result = new HashMap<>();
//        result.put("posts", posts);
//
//        return result;
//    }

    //전체 글 출력
    @GetMapping("/")
    public List<PostResponseDto> allPosts() {
        return postService.getAllPosts();
    }

}