package com.atguigu.spzx.manger.mapper;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: ProductSpecMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/9 下午10:19
 * Version 1.8
 */

@Mapper
public interface ProductSpecMapper {
    //列表
    List<ProductSpec> findByPage();

    //添加
    void save(ProductSpec productSpec);

    //修改
    void update(ProductSpec productSpec);

    //删除
    void delete(Long id);

    //查询所有
    List<ProductSpec> findAll();
}
