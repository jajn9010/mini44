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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Transactional
    public PostResponseDto findPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        String title = post.getTitle();
        String content = post.getContent();
        String location = post.getLocation();
//        String nickname = post.getUser().getNickname();
        String imageUrl = post.getImageUrl();
        LocalDateTime createdAt = post.getCreatedAt();
        List<Comment> comments = commentRepository.findAllByPost_PostId(postId);
        System.out.println(comments);

        return new PostResponseDto(postId, title, content, location, imageUrl, createdAt, comments);
    }

    public PostResponseDto allPosts(Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 하는 글이 없습니다."));

        PostResponseDto postResponseDto = new PostResponseDto(
                    post.getPostId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getLocation(),
                    post.getImageUrl(),
//                    post.getUser().getNickname(),
                post.getCreatedAt(),
                post.getComments()
        );

        return postResponseDto;
    }

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
//                    post.getUser().getNickname(),
                    post.getCreatedAt(),
                    post.getComments()
            );

            postResponseDtos.add(postResponseDto);
        }

        return postResponseDtos;
    }

    //    @Transactional
//        public Post savePost(PostRequestDto postRequestDto) {
//        Post post = new Post(postRequestDto);
//        postRepository.save(post);
//        return post;

//    }


//    @Transactional
//    public void createPost(UserDetailsImpl userDetails, PostRequestDto postRequestDto) {
////        User user = ValidateChecker.userDetailsIsNull(userDetails);
//        Post post = new Post(user,postRequestDto);
//        postRepository.save(post);
//    }

    @Transactional
    public String updatePost(PostRequestDto postRequestDto, Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );
        if(post.getUser().getUserId().equals(userDetails.getUser().getUserId())) {
            post.updatePost(postRequestDto);
        }
        return "게시글 수정 완료";
    }

    public String deletePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("삭제할 게시글이 존재하지 않습니다.")
        );
//        if(post.getUser().getUserId().equals(userDetails.getUser().getUserId())) {

//        }
        postRepository.deleteById(postId);
        return "게시글 삭제 완료";
    }


//    public ResponseEntity<String> updateUserImage(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        System.out.println(multipartFile);
//        String image = s3Uploader.uploadFile(multipartFile, "static");
//        System.out.println("oooo!!"+image);
//        String imageUrl =  image;
//        System.out.println("xxxx");
//        return ResponseEntity.ok()
//                .body(image);
//    }

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public ImageDto uploadFile(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: MultipartFile -> File convert fail"));
        return upload(uploadFile, dirName);

    }

    public ImageDto upload(File uploadFile, String filePath) {
        System.out.println(uploadFile);
        String fileName = filePath + "/" + UUID.randomUUID() + uploadFile.getName();   // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // s3로 업로드
//        String imageUrl = "https://mini-pro.s3.ap-northeast-2.amazonaws.com/static/" + uploadImageUrl;
        removeNewFile(uploadFile);
        return new ImageDto(fileName,uploadImageUrl);

    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {

        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

        String nqq = amazonS3Client.getUrl(bucket, fileName).toString();

        System.out.println(nqq);

        return amazonS3Client.getUrl(bucket, fileName).toString();

    }

    // 로컬에 저장된 이미지 지우기

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            System.out.println("File delete success");
            return;
        }
        System.out.println("File delete fail");
    }

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            System.out.println("test");
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }


    //        게시글 작성
    @Transactional
    public void createPost(PostRequestDto postRequestDto, String dirName) throws Exception{

        File uploadFile = convert(postRequestDto.getImageUrl()).orElseThrow(
                () -> new IllegalArgumentException("이미지없음")
                );
        ImageDto imageDto = upload(uploadFile, dirName);
        imageDto.setTitle(postRequestDto.getTitle());
        imageDto.setContent(postRequestDto.getContent());
        imageDto.setLocation(postRequestDto.getLocation());
        imageDto.setUser(postRequestDto.getUser());
//        Long userId = userDetails.getId;

        Post post = new Post(imageDto);

        postRepository.save(post);
        

    }
}


