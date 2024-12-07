package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: ProductDetailsMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/13 下午8:33
 * Version 1.8
 */

@Mapper
public interface ProductDetailsMapper {

    //4 productId,获取商品详细信息
    ProductDetails getByProductId(Long productId);
}
