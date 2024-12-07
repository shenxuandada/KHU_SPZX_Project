package com.atguigu.spzx.manger.mapper;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: ProductMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/10 下午3:59
 * Version 1.8
 */

@Mapper
public interface ProductMapper {

    //列表(条件分页查询)
    List<Product> findByPage(ProductDto productDto);

    // 保存商品基本薪 product表
    void save(Product product);

    // 根据id查询商品基本信息 product
    Product findProductById(Long id);

    //修改products
    void updateById(Product product);

    //1 根据商品id删除product表
    void deleteById(Long id);
}
