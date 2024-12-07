package com.atguigu.spzx.manger.mapper;

import com.atguigu.spzx.model.entity.base.ProductUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: ProductUnitMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/10 下午6:15
 * Version 1.8
 */

@Mapper
public interface ProductUnitMapper {
    List<ProductUnit> findAll();
}
