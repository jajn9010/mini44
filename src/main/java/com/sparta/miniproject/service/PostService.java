package com.sparta.miniproject.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.miniproject.dto.ImageDto;
import com.sparta.miniproject.dto.PostRequestDto;
import com.sparta.miniproject.dto.PostResponseDto;
import com.sparta.miniproject.model.Comment;
import com.sparta.miniproject.model.Post;
import com.sparta.miniproject.repository.CommentRepository;
import com.sparta.miniproject.repository.PostRepository;
import com.sparta.miniproject.repository.UserRepository;
import com.sparta.miniproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AmazonS3Client amazonS3Client;

    public List<PostResponseDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        for (Post post : posts) {
            PostResponseDto postResponseDto = new PostResponseDto(
                    post.getPostId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getLocation(),
                    post.getImageUrl(),
                    post.getUser().getNickname(),
                    post.getCreatedAt(),
                    post.getComments()
            );
            postResponseDtos.add(postResponseDto);
        }
        return postResponseDtos;
    }



    @Transactional
    public PostResponseDto findPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("???????????? ???????????? ????????????.")
        );

        String title = post.getTitle();
        String content = post.getContent();
        String location = post.getLocation();
        String imageUrl = post.getImageUrl();
        String nickname = post.getUser().getNickname();
        LocalDateTime createdAt = post.getCreatedAt();
        List<Comment> comments = commentRepository.findAllByPost_PostIdOrderByCommentIdDesc(postId);

        return new PostResponseDto(postId, title, content, location, imageUrl, nickname, createdAt, comments);
    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public ImageDto upload(File uploadFile, String filePath) {
        String fileName = filePath + "/" + UUID.randomUUID() + uploadFile.getName();   // S3??? ????????? ?????? ??????
        String uploadImageUrl = putS3(uploadFile, fileName); // s3??? ?????????
//        String imageUrl = "https://mini-pro.s3.ap-northeast-2.amazonaws.com/static/" + uploadImageUrl;
        removeNewFile(uploadFile);
        return new ImageDto(fileName,uploadImageUrl);
    }

    // S3??? ?????????
    private String putS3(File uploadFile, String fileName) {

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

        String nqq = amazonS3Client.getUrl(bucket, fileName).toString();

        System.out.println(nqq);

        return amazonS3Client.getUrl(bucket, fileName).toString();

    }

    // ????????? ????????? ????????? ?????????

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            System.out.println("File delete success");
            return;
        }
        System.out.println("File delete fail");
    }

    // ????????? ?????? ????????? ??????
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // ?????? ????????? ????????? ????????? File??? ????????? (????????? ?????????????????? ?????? ?????????)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream ???????????? ????????? ????????? ??????????????? ???????????? ??????
                fos.write(file.getBytes());
            }
            System.out.println("test");
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    //        ????????? ??????
    @Transactional
    public void createPost(PostRequestDto postRequestDto, String dirName) throws Exception{

        File uploadFile = convert(postRequestDto.getImageUrl()).orElseThrow(
                () -> new IllegalArgumentException("???????????????")
        );
        ImageDto imageDto = upload(uploadFile, dirName);
        imageDto.setTitle(postRequestDto.getTitle());
        imageDto.setContent(postRequestDto.getContent());
        imageDto.setLocation(postRequestDto.getLocation());
        imageDto.setUser(postRequestDto.getUser());

        Post post = new Post(imageDto);

        postRepository.save(post);
    }

    @Transactional
    public String updatePost(PostRequestDto postRequestDto, String dirName, Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("???????????? ???????????? ????????????.")
        );
        if(post.getUser().getUsername().equals(userDetails.getUser().getUsername())) {
            File uploadFile = convert(postRequestDto.getImageUrl()).orElseThrow(
                    () -> new IllegalArgumentException("??????????????????!")
            );
            ImageDto imageDto = upload(uploadFile, dirName);
            imageDto.setTitle(postRequestDto.getTitle());
            imageDto.setContent(postRequestDto.getContent());
            imageDto.setLocation(postRequestDto.getLocation());
            imageDto.setUser(userDetails.getUser());

            post.updatePost(postId, imageDto);
        }
        return "????????? ?????? ??????";
    }

    public String deletePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("????????? ???????????? ???????????? ????????????.")
        );
        if(post.getUser().getUsername().equals(userDetails.getUser().getUsername())) {
            postRepository.deleteById(postId);
        }
        return "????????? ?????? ??????";
    }
}