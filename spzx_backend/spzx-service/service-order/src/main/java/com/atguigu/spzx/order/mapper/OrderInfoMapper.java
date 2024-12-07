package com.atguigu.spzx.order.mapper;

import com.atguigu.spzx.model.entity.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: OrderInfoMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/17 下午4:11
 * Version 1.8
 */

@Mapper
public interface OrderInfoMapper {

    // 添加数据到order_info表
    void save(OrderInfo orderInfo);

    // 获取订单信息
    OrderInfo getById(Long orderId);

    //根据userid和订单状态进行查询
    List<OrderInfo> findUserPage(Long userId, Integer orderStatus);

    // 远程调用:根据订单编号获取订单信息
    OrderInfo getOrderInfoByOrderNo(String orderNo);
}
