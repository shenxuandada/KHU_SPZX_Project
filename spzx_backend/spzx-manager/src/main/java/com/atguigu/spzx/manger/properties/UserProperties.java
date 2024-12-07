package com.atguigu.spzx.manger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * ClassName: UserProperties
 * Description:
 *
 * @Author Refactoring
 * Create 2024/7/31 下午6:36
 * Version 1.8
 */
@Data
@ConfigurationProperties(prefix = "spzx.auth")
public class UserProperties {
    private List<String> noAuthUrls;
}
