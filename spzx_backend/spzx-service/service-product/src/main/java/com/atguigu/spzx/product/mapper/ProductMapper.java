package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: ProductMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/13 下午8:32
 * Version 1.8
 */

@Mapper
public interface ProductMapper {

    //3 根据第二步获取sku,从sku获取productId,获取商品信息
    Product getById(Long productId);
}
