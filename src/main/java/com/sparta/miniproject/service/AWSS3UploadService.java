//package com.sparta.miniproject.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.sparta.miniproject.model.S3Component;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import java.io.InputStream;
//
//@RequiredArgsConstructor
//@Component
//public class AWSS3UploadService implements UploadService{
//
//    private final AmazonS3 amazonS3;
//    private final S3Component component;
//
//    @Override
//    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
//        amazonS3.putObject(new PutObjectRequest(component.getBucket(),fileName,inputStream,objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
//    }
//
//    @Override
//    public String getFileUrl(String fileName) {
//       return amazonS3.getUrl(component.getBucket(), fileName).toString();
//    }
//
//
//}
