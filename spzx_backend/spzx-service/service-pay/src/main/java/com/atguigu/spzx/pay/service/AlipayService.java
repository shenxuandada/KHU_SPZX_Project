package com.atguigu.spzx.pay.service;


/**
 * ClassName: AlipayService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/18 下午5:24
 * Version 1.8
 */


public interface AlipayService {

    //支付宝下单
    String submitAlipay(String orderNo);
}
