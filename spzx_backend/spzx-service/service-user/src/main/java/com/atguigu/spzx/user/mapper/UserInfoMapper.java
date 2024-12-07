package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: UserInfoMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/15 下午8:21
 * Version 1.8
 */

@Mapper
public interface UserInfoMapper {

    //3 校验用户名不能重复
    UserInfo selectByUsername(String username);

    // 注册
    void save(UserInfo userInfo);
}
