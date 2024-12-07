package com.atguigu.spzx.manger.mapper;

import com.atguigu.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * ClassName: BrandMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/8 下午9:25
 * Version 1.8
 */

@Mapper
public interface BrandMapper {

    //列表
    List<Brand> findByPage();

    //添加
    void save(Brand brand);

    //修改
    void updateById(Brand brand);

    //删除
    void deleteById(Long id);

}