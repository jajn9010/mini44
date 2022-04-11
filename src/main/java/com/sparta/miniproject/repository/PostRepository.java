package com.sparta.miniproject.repository;

import com.sparta.miniproject.dto.PostRequestDto;
import com.sparta.miniproject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll();
}
