package com.atguigu.spzx.feign.cart;

import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * ClassName: CartFeignClient
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/17 下午4:52
 * Version 1.8
 */

@FeignClient(value = "service-cart")
public interface CartFeignClient {

    @GetMapping("/api/order/cart/auth/deleteChecked")
    public Result deleteChecked();

    @GetMapping(value = "/api/order/cart/auth/getAllCkecked")
    public List<CartInfo> getAllCkecked();


}
