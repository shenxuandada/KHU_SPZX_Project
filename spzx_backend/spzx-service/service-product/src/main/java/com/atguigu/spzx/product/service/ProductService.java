package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * ClassName: ProductService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/12 下午6:04
 * Version 1.8
 */
public interface ProductService {

    //2 根据销量排序,获取前十条记录
    List<ProductSku> selectProductSkuBySale();

    // 分页查询
    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    //商品详情接口
    ProductItemVo item(Long skuId);

    //远程调用: 根据skuId返回sku信息
    ProductSku getBySkuId(Long skuId);
}
