package com.atguigu.spzx.manger.mapper;

import com.atguigu.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: ProductDetailsMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/10 下午6:34
 * Version 1.8
 */

@Mapper
public interface ProductDetailsMapper {

    //添加
    void save(ProductDetails productDetails);

    //3 根据id删除商品详细数据 product_details
    ProductDetails findProductDetailsById(Long id);

    //修改product_details
    void updateById(ProductDetails productDetails);

    //3 根据商品id删除product_details表
    void deleteByProductId(Long id);
}
