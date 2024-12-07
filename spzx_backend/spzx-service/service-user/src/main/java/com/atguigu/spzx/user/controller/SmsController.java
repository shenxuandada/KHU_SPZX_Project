package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: SmsController
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/15 下午6:12
 * Version 1.8
 */

@RestController
@RequestMapping("api/user/sms")
public class SmsController {

    @Autowired
    protected SmsService smsService;

    @GetMapping(value = "/sendCode/{phone}")
    private Result sendCode(@PathVariable String phone) {
        smsService.sendCode(phone);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
