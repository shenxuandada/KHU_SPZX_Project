package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.entity.user.UserAddress;

import java.util.List;

/**
 * ClassName: UserAddressService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/17 下午3:56
 * Version 1.8
 */
public interface UserAddressService {

    // 获取用户地址列表
    List<UserAddress> findUserAddressList();

    // 根据id获得收货地址信息
    UserAddress getUserAddress(Long id);
}
