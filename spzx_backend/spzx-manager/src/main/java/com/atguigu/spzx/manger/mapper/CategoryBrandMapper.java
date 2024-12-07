package com.atguigu.spzx.manger.mapper;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: CategoryBrandMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/9 下午4:07
 * Version 1.8
 */

@Mapper
public interface CategoryBrandMapper {
    //分类品牌条件分页查询
    List<CategoryBrand> findByPage(CategoryBrandDto categoryBrandDto);

    //添加
    void save(CategoryBrand categoryBrand);

    //删除
    void deleteById(Long id);

    //修改
    void updateById(CategoryBrand categoryBrand);

    //根据分类id查询对应品牌数据
    List<Brand> findBrandByCategoryId(Long categoryId);
}
