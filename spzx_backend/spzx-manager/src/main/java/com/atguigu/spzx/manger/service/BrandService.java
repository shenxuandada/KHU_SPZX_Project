package com.atguigu.spzx.manger.service;

import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * ClassName: BrandService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/8 下午9:27
 * Version 1.8
 */
public interface BrandService {
    //列表
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    //添加
    void save(Brand brand);

    //修改
    void updateById(Brand brand);

    //删除
    void deleteById(Long id);

    //查询所有品牌
    List<Brand> findAll();
}
