package com.atguigu.spzx.manger.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * ClassName: CategoryMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/7 下午5:33
 * Version 1.8
 */

@Mapper
public interface CategoryMapper {

    //1 根据id条件之进行查询,返回List集合
    List<Category> selectCategoryByParentId(Long id);

    //判断每个分类是否有下一层分类
    int selectCountByParentId(Long id);

    //2 调用mapper方法查询所有分类,返回list集合
    List<Category> findAll();

    //批量保存的方法
    void batchInsert(List<CategoryExcelVo> categoryList);
}
