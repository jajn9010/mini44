package com.sparta.miniproject.service;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface UploadService {
    void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName);

    String getFileUrl(String fileName);

}

//파일업로드 서비스에서 세부 구현 사항인 AWSS#업로드 서비스가 아닌 인터페이슨 업로드 서비스에 의존관계
//맺음으로써 차후 AWS S3가 아닌 다른 서비스를 이용하거나 다른방법으로 업로드를 구현할 때 유연하게
// 변경 가능
//추가적으로 인터페이스로 분리함으로써 테스트시 의존성을 끊기 위해 (테스트마다 실제로 S#에 업로드되면 곤란)
//객체를 모킹하지않고 런타임시에 Stub객체를 생성하는 등 테스트 작성 시에도 장점이 있다.