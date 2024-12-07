package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: CategoryController
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/12 下午10:05
 * Version 1.8
 */



@RestController
@RequestMapping(value="/api/product/category")
//@CrossOrigin //跨域
public class CategoryController {

    @Autowired
    public CategoryService categoryService;

    //查询所有分类,树形封装
    @GetMapping("/findCategoryTree")
    public Result findCategoryTree(){
        List<Category>list= categoryService.findCategoryTree();
        return Result.build(list, ResultCodeEnum.SUCCESS);

    }
}
