package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.IndexVo;
import com.atguigu.spzx.product.service.CategoryService;
import com.atguigu.spzx.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.checkerframework.checker.index.qual.GTENegativeOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ClassName: IndexController
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/12 下午6:01
 * Version 1.8
 */


@Tag(name = "首页接口管理")
@RestController
@RequestMapping(value = "/api/product/index")
//@CrossOrigin //解决跨域问题
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public Result index() {
        //1 所有一级分类
        List<Category> categoryList = categoryService.selectOneCategory();

        //2 根据销量排序,获取前十条记录
        List<ProductSku> productSkuList = productService.selectProductSkuBySale();

        //3 封装数据到Vo对象中
        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);

    }


}
