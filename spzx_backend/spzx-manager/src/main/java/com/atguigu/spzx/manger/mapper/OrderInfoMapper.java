package com.atguigu.spzx.manger.mapper;

import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: OrderInfoMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/11 下午4:26
 * Version 1.8
 */

@Mapper
public interface OrderInfoMapper {
    //统计前一天交易金额
    OrderStatistics selectStatisticsByDate(String creatDate);
}
