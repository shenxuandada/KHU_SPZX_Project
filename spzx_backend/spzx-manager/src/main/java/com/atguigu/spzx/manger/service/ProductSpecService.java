package com.atguigu.spzx.manger.service;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: ProductSpecService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/9 下午10:16
 * Version 1.8
 */


public interface ProductSpecService {

    //列表
    PageInfo<ProductSpec> findByPage(Integer page, Integer limit);

    //添加
    void save(ProductSpec productSpec);

    //修改
    void updateById(ProductSpec productSpec);

    //修改
    void deleteById(Long id);

    //查询所有
    List<ProductSpec> findAll();
}
