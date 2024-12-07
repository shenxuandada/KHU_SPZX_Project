package com.atguigu.spzx.manger.controller;

import com.atguigu.spzx.manger.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.events.Event;

import java.util.List;

/**
 * ClassName: CategoryController
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/7 下午5:25
 * Version 1.8
 */

@RestController
@RequestMapping(value = "/admin/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //导入
    @PostMapping("/importData")
    public Result importData(MultipartFile file)  {
        categoryService.importData(file);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //导出
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response){
        categoryService.exportData(response);
    }

    //分类列表,每次只查询一层数据
    //SELECT * FROM category WHERE parent_id=id
    @GetMapping("/findCategoryList/{id}")
    public Result findCategoryList(@PathVariable Long id) {
        List<Category> list= categoryService.findCategoryList(id);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
