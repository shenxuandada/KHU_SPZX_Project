package com.atguigu.spzx.order.mapper;

import com.atguigu.spzx.model.entity.order.OrderLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: OrderLogMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/17 下午5:55
 * Version 1.8
 */

@Mapper
public interface OrderLogMapper {

    //6 添加数据到order_Log表
    void save(OrderLog orderLog);
}
