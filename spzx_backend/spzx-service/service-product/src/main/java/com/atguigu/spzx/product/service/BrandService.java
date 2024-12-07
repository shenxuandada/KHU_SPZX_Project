package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.entity.product.Brand;

import java.util.List;

/**
 * ClassName: BrandService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/13 下午7:20
 * Version 1.8
 */
public interface BrandService {

    // 获取所有品牌
    List<Brand> findAll();
}
