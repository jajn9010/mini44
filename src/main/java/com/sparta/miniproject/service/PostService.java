package com.sparta.miniproject.service;

import com.sparta.miniproject.dto.PostResponseDto;
import com.sparta.miniproject.model.Post;
import com.sparta.miniproject.repository.PostRepository;
import jdk.javadoc.internal.doclets.formats.html.Contents;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto allPosts(Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 하는 글이 없습니다."));

        PostResponseDto postResponseDto = new PostResponseDto(
                    post.getPostId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getLocation(),
                    post.getImageUrl(),
                    post.getNickName(),
                    post.getComments()
        );

        return postResponseDto;
    }

    public List<PostResponseDto> getAllPosts() {

        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDto postResponseDto = new PostResponseDto(
                    post.getPostId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getLocation(),
                    post.getImageUrl(),
                    post.getNickName(),
                    post.getComments()
            );

            postResponseDtos.add(postResponseDto);
        }

        return postResponseDtos;
    }

    @Transactional
    public Post savePost(PostResponseDto postResponseDto) {
        Post post = new Post(postResponseDto);
        postRepository.save(post);
        return post;
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
    public Page<Post> home(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable);
    }
}



