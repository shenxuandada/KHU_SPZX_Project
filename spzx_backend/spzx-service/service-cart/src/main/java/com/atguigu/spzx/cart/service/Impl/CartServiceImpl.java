package com.atguigu.spzx.cart.service.Impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: CartServiceImpl
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/16 下午7:59
 * Version 1.8
 */

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;


    private String getCartKey(Long userId) {
        //定义key user:cart:userId
        return "user:cart:" + userId;
    }

    //添加购物车
    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        // 1必须登录状态,获取当前登录用户id(作为redis的hash类型的key值)
        // 从threadLocal获取用户信息
        Long userId = AuthContextUtil.getUserInfo().getId();
        // 构建hash类型key名称
        String cartKey = this.getCartKey(userId);

        // 2 因为购物车放到redis里面
        // hash类型 key:userId filed:skuId value:sku信息CartInfo
        // 从redis里面获取购物车数据,根据用户id+skuId获取(hash类型key+field)
        Object cartInfoObj =
                redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));

        // 3 如果购物车存在添加商品,把商品数量增加
        CartInfo cartInfo = null;
        if (cartInfoObj != null) {// 添加购物车商品已经存在的,把商品数量相加
            // careInfoObj ---- CartInfo
            cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            // 数量相加
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            // 设置属性 表示购物车商品选中状态
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        } else {
            // 4 如果购物车没有添加商品.直接商品添加购物车(添加到redis里)
            // 远程调用实现,用过nacos + openFeign实现 根据skuId获取sku信息
            cartInfo = new CartInfo();

            // 远程调用实现:根据skuId获取商品sku信息
            ProductSku productSku = productFeignClient.getBySkuId(skuId);
            // 设置相关数据到cartInfo对象中
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
        // 添加redis中
        redisTemplate.opsForHash().put(cartKey, String.valueOf(skuId), JSON.toJSONString(cartInfo));


    }

    // 查询购物车
    @Override
    public List<CartInfo> getCartList() {
        //1 构建查询redis里面的key值,根据当前userId
        long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //2 根据key从redis里面hash类型获取所有value值 cartInfo
        List<Object> valueList = redisTemplate.opsForHash().values(cartKey);

        // List<Object> -----List<valueList>
        if (!CollectionUtils.isEmpty(valueList)) {
            List<CartInfo> cartInfoList = valueList.stream().map(cartInfoObj ->
                            JSON.parseObject(cartInfoObj.toString(), CartInfo.class))
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .collect(Collectors.toList());
            return cartInfoList;
        }
        // []
        return new ArrayList<>();
    }

    // 删除购物车商品
    @Override
    public void delete(String skuId) {
        long userId = AuthContextUtil.getUserInfo().getId();
        String CartKey = this.getCartKey(userId);
        redisTemplate.opsForHash().delete(CartKey, String.valueOf(skuId));

    }

    // 更新购物车商品选中状态
    @Override
    public void checkCar(Long skuId, Integer isChecked) {
        //1 构建查询的redis里面key值,根据当前userId
        long userId = AuthContextUtil.getUserInfo().getId();
        String CartKey = this.getCartKey(userId);

        //2 判断key是否包含filed
        Boolean hasKey = redisTemplate.opsForHash().hasKey(CartKey, String.valueOf(skuId));
        if (hasKey) {
            //3 根据key+field把value获取出来
            String cartInfoString = redisTemplate.opsForHash().get(CartKey, String.valueOf(skuId)).toString();

            //4 更新value里面选中状态
            CartInfo cartInfo = JSON.parseObject(cartInfoString, CartInfo.class);
            cartInfo.setIsChecked(isChecked);

            //5 放回到redis的hash类型里面
            redisTemplate.opsForHash().put(CartKey,
                    String.valueOf(skuId),
                    JSON.toJSONString(cartInfo));
        }
    }

    // 更新购物车商品全部选中状态
    @Override
    public void allCheckCart(Integer isChecked) {
        // 1 构建查询的redis里面key值,根据当前userId
        long userId = AuthContextUtil.getUserInfo().getId();
        String CartKey = this.getCartKey(userId);

        // 2 根据key获取购物车所有value值
        List<Object> objectList = redisTemplate.opsForHash().values(CartKey);

        // 判断不为空
        if (!CollectionUtils.isEmpty(objectList)) {
            //List<Object> ------List<cartInfo>
            List<CartInfo> cartInfoList = objectList.stream().map(Object -> JSON.parseObject(objectList.toString(), CartInfo.class))
                    .collect(Collectors.toList());
            // 3把每个商品isChecked进行更新
            cartInfoList.forEach(cartInfo -> {
                cartInfo.setIsChecked(isChecked);
                redisTemplate.opsForHash().put(CartKey, String.valueOf(cartInfo.getSkuId()),
                        JSON.toJSONString(cartInfo));
            });
        }
    }

    // 清空购物车
    @Override
    public void clearCart() {
        // 1 构建查询的redis里面key值,根据当前userId
        long userId = AuthContextUtil.getUserInfo().getId();
        String CartKey = this.getCartKey(userId);

        //根据key删除redis里面数据
        redisTemplate.delete(CartKey);

    }

    // 远程调用: 订单结算使用,获取购物车选中商品列表
    @Override
    public List<CartInfo> getAllCkecked() {
        // 获取userId,构建key
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        // 根据key获取购物车所有商品
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        if (!CollectionUtils.isEmpty(objectList)) {
            List<CartInfo> cartInfoList = objectList.stream().map(object ->
                            JSON.parseObject(object.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)//购物车选中
                    .collect(Collectors.toList());
            return cartInfoList;
        }
        return new ArrayList<>();
    }

    // 远程调用: 删除生成订单的购物车商品
    @Override
    public void deleteChecked() {
        // 1 获取userId构建key
        long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = this.getCartKey(userId);

        //根据key获取redis所有value值
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        // 删除选中商品
        objectList.stream().map(object -> JSON.parseObject(object.toString(), CartInfo.class))
                .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                .forEach(cartInfo -> redisTemplate.opsForHash().delete(cartKey,
                        String.valueOf(cartInfo.getSkuId())));
    }
}
