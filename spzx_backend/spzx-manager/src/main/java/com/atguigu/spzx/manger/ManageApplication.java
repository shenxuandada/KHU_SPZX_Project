package com.atguigu.spzx.manger;

import com.atguigu.spzx.common.log.annotation.EnableLogAspect;
import com.atguigu.spzx.manger.properties.MinioProperties;
import com.atguigu.spzx.manger.properties.UserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ClassName: ManageApplication
 * Description:
 *
 * @Author Refactoring
 * Create 2024/7/29 下午10:20
 * Version 1.8
 */

@EnableLogAspect
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.spzx"})
@EnableConfigurationProperties(value = {UserProperties.class, MinioProperties.class})
@EnableScheduling
public class ManageApplication {
    public static void main(String[] args) {SpringApplication.run(ManageApplication.class, args);
    }
}
