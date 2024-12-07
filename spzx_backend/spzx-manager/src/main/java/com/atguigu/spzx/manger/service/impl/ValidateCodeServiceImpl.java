package com.atguigu.spzx.manger.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.atguigu.spzx.manger.service.ValidateCodeService;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: ValidateCodeServiceImpl
 * Description:
 *
 * @Author Refactoring
 * Create 2024/7/30 下午6:08
 * Version 1.8
 */

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    //生成图片验证码
    @Override
    public ValidateCodeVo generateValidateCode() {
        //1 通过工具生成图片验证码
        //hutool
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 20);
        String codeValue = circleCaptcha.getCode();//4位验证码
        String imageBase64 = circleCaptcha.getImageBase64();//返回图片验证码,base64编码

        //2 把验证码储存到redis里,设置redis的key: uuid redis的value :验证码值
        //设置过期时间
        String key = UUID.randomUUID().toString().replaceAll("-","");
        redisTemplate.opsForValue().set("user:validate"+key,
                codeValue,
                5,
                TimeUnit.MINUTES);

        //3 返回ValidateCodeVo对象
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key); //redis存储数据key
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);

        return validateCodeVo;
    }
}
