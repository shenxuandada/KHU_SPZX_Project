package com.atguigu.spzx.pay.service;

import com.atguigu.spzx.model.entity.pay.PaymentInfo;

import java.util.Map;

/**
 * ClassName: PaymentInfoService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/18 下午5:25
 * Version 1.8
 */
public interface PaymentInfoService {

    // 保存支付记录
    PaymentInfo savePaymentInfo(String orderNo);

    // 支付完成,更新状态
    void updatePaymentStatus(Map<String, String> paramMap);
}
