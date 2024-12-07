package com.atguigu.spzx.pay.mapper;

import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: PaymentInfoMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/18 下午5:21
 * Version 1.8
 */

@Mapper
public interface PaymentInfoMapper {

    // 1根据订单编号查询支付记录
    PaymentInfo getByOrderNo(String orderNo);

    // 添加
    void save(PaymentInfo paymentInfo);


    void updatePaymentInfo(PaymentInfo paymentInfo);
}
