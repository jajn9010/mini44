package com.sparta.miniproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties(prefix = "cloud.aws.s3")//동작은되는데 인텔리에서 에러표시뜬다고함.
@Component
public class S3Component {

    private String bucket;
}
