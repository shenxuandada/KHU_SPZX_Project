package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: BrandMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/13 下午7:23
 * Version 1.8
 */


@Mapper
public interface BrandMapper {
    List<Brand> findAll();
}
