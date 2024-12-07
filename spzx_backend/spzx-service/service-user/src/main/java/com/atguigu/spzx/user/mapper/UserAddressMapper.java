package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: UserAddressMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/17 下午3:54
 * Version 1.8
 */

@Mapper
public interface UserAddressMapper {

    // 获取用户地址列表
    List<UserAddress> findUserAddressList(Long userId);

    // 根据id获得收货地址信息
    UserAddress getUserAddress(Long id);
}
