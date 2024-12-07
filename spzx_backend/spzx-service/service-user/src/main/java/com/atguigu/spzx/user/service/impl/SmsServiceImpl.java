package com.atguigu.spzx.user.service.impl;


import com.atguigu.spzx.user.service.SmsService;

import com.atguigu.spzx.utils.HttpUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.apache.http.HttpResponse;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: SmsServiceImpl
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/15 下午6:11
 * Version 1.8
 */

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void sendCode(String phone) {



        //TODO 固定验证码,为了省钱,后期可以修改回去
//        String code= redisTemplate.opsForValue().get(phone);
//        if(StringUtils.hasText(code)){
//            // 验证码已存在，直接返回，避免重复发送
//            return;
//        }
        //固定验证码,为了省钱,后期可以修改回去


        //1 生成验证码
        String code = RandomStringUtils.randomNumeric(4);
        //2 把生成验证码放到redis中,设置过期时间
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
        //3 向手机号发送验证码
        sendMessage(phone, code);


    }

    //3 发送短信验证码方法
    private void sendMessage(String phone, String code) {
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "e7ede6229f41499b830fec1faa81ea3e";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        querys.put("param", "**code**:" + code + ",**minute**:5");

//smsSignId（短信前缀）和templateId（短信模板），可登录国阳云控制台自助申请。参考文档：http://help.guoyangyun.com/Problem/Qm.html

        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<String, String>();

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从\r\n\t    \t* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java\r\n\t    \t* 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
