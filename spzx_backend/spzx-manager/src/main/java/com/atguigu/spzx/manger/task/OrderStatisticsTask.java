package com.atguigu.spzx.manger.task;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manger.mapper.OrderInfoMapper;
import com.atguigu.spzx.manger.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * ClassName: OrderStatisticsTask
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/11 下午4:05
 * Version 1.8
 */

@Component
public class OrderStatisticsTask {

//    //测试定时任务
//    @Scheduled(cron = "0/5 * * * * ? ")
//    public void task(){
//        System.out.println(new Date().toInstant());
//    }



    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    //每天凌晨两点查询前一天统计数据,把统计之后的数据添加到统计结果表里面
    @Scheduled(cron = "0 0 2 * * ?")
//    @Scheduled(cron = "0/5 * * * * ? ") //TODO 测试用
    public void orderTotalAmountStatistics(){
        System.out.println(new Date().toInstant());


        //1 获取前一天数据
        String creatDate = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");

        //2 根据前一天日期进行统计功能(分组操作)
        //统计前一天交易金额
        OrderStatistics orderStatistics = orderInfoMapper.selectStatisticsByDate(creatDate);

        //3 把统计后的数据添加统计结果表中
        if(orderStatistics != null){
            orderStatisticsMapper.insert(orderStatistics);
        }
    }
}
