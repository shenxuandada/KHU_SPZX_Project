package com.atguigu.spzx.manger.mapper;

import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: OrderStatisticsMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/11 下午4:26
 * Version 1.8
 */

@Mapper
public interface OrderStatisticsMapper {

    //3 把统计后的数据添加统计结果表中
    void insert(OrderStatistics orderStatistics);

    //根据dto条件查询统计结果数据,返回list集合
    List<OrderStatistics> selectList(OrderStatisticsDto orderStatisticsDto);
}

