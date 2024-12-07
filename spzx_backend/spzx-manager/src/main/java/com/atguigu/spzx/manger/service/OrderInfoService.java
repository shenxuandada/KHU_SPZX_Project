package com.atguigu.spzx.manger.service;

import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.vo.order.OrderStatisticsVo;

/**
 * ClassName: OrderInfoService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/11 下午5:23
 * Version 1.8
 */


public interface OrderInfoService {

    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
