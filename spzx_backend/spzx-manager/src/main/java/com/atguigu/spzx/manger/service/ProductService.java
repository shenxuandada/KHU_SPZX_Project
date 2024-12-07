package com.atguigu.spzx.manger.service;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

/**
 * ClassName: ProductService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/10 下午3:58
 * Version 1.8
 */
public interface ProductService {

    //列表(条件分页查询)
    PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto);

    //添加
    void save(Product product);

    //根据商品id查询商品信息
    Product getById(Long id);

    //保存修改数据
    void update(Product product);

    //删除
    void deleteById(Long id);

    //审核
    void updateAuditStatus(Long id, Integer auditStatus);

    //商品上下架
    void updateStatus(Long id, Integer status);
}
