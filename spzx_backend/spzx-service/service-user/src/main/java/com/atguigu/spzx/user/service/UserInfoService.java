package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;

/**
 * ClassName: UserInfoService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/15 下午8:14
 * Version 1.8
 */
public interface UserInfoService {

    // 注册
    void register(UserRegisterDto userRegisterDto);

    // 登录
    String login(UserLoginDto userLoginDto);

    //获取当前登录用户信息
    UserInfoVo getCurrentUserInfo(String token);
}
