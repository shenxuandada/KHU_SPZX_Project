package com.atguigu.spzx.manger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ClassName: MinioProperties
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/4 下午7:58
 * Version 1.8
 */

@Data
@ConfigurationProperties(prefix = "spzx.minio")
public class MinioProperties {

    private String endpointUrl;
    private String accessKey;
    private String secreKey;
    private String bucketName;
}
