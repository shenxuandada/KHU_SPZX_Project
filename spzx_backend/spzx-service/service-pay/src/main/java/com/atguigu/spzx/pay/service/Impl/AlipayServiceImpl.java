package com.atguigu.spzx.pay.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.pay.service.AlipayService;
import com.atguigu.spzx.pay.service.PaymentInfoService;
import com.atguigu.spzx.pay.utils.AlipayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * ClassName: AlipayServiceImpl
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/18 下午5:26
 * Version 1.8
 */

@Service
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private AlipayClient alipayClient;

    //支付宝下单
    @Override
    public String submitAlipay(String orderNo) {

        //1 保存支付记录
        PaymentInfo paymentInfo = paymentInfoService.savePaymentInfo(orderNo);

        //2 调用支付宝接口
        //构建需要参数,进行调用
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

        // 同步回调
        alipayRequest.setReturnUrl(alipayProperties.getReturnPaymentUrl());

        // 异步回调
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyPaymentUrl());

        // 准备请求参数 ，声明一个map 集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("out_trade_no", paymentInfo.getOrderNo());
        map.put("product_code", "QUICK_WAP_WAY");
        // TODO 打开下面的代码可以实际支付价格(记得要把下下面的注释掉)
        //map.put("total_amount",paymentInfo.getAmount());
        map.put("total_amount", new BigDecimal("0.01"));
        map.put("subject", paymentInfo.getContent());
        alipayRequest.setBizContent(JSON.toJSONString(map));

        // 调用支付宝服务接口
        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest);
            if (response.isSuccess()) {
                String form = response.getBody();
                return form;
            } else {
                throw new GuiguException(ResultCodeEnum.DATA_ERROR);
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }
}
