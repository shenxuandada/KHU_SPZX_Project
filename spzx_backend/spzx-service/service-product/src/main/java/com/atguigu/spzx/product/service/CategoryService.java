package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.entity.product.Category;

import java.util.List;

/**
 * ClassName: CategoryService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/12 下午6:03
 * Version 1.8
 */
public interface CategoryService {

    //1 所有一级分类
    List<Category> selectOneCategory();

    //查询所有分类,树形封装
    List<Category> findCategoryTree();
}
