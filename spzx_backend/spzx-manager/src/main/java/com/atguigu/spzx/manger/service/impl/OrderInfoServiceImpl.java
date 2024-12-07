package com.atguigu.spzx.manger.service.impl;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manger.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.manger.service.OrderInfoService;
import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import com.atguigu.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: OrderInfoServiceImpl
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/11 下午5:23
 * Version 1.8
 */

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    //返回统计数据
    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        //根据dto条件查询统计结果数据,返回list集合
        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectList(orderStatisticsDto);

        //遍历list集合,得到所有日期,把所有日期封装list新集合里面
        List<String> dateList = orderStatisticsList.stream()
                .map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd"))
                .collect(Collectors.toList());

        List<BigDecimal> decimalList =
                orderStatisticsList.stream()
                        .map(OrderStatistics::getTotalAmount)
                        .collect(Collectors.toList());

        //把两个list集合封装OrderStatisticsVo,返回OrderStatisticsVo
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(decimalList);
        return orderStatisticsVo;
    }
}
