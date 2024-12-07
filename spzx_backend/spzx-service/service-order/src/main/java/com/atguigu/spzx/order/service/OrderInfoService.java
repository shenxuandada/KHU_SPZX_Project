package com.atguigu.spzx.order.service;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.github.pagehelper.PageInfo;

/**
 * ClassName: OrderInfoService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/17 下午4:09
 * Version 1.8
 */
public interface OrderInfoService {

    // 结算
    TradeVo getTrade();

    // 生成订单接口
    Long submitOrder(OrderInfoDto orderInfoDto);

    // 获取订单信息
    OrderInfo getOrderInfo(Long orderId);

    // 立即购买
    TradeVo buy(Long skuId);

    // 获取订单分页列表
    PageInfo<OrderInfo> findOrderPage(Integer page, Integer limit, Integer orderStatus);

    // 远程调用:根据订单编号获取订单信息
    OrderInfo getOrderInfoByOrderNo(String orderNo);
}
