package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.mapper.ProductSkuMapper;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: ProductController
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/13 下午7:31
 * Version 1.8
 */

@RestController
@RequestMapping(value = "/api/product")
//@CrossOrigin //解决跨域问题
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductSkuMapper productSkuMapper;

    //商品详情接口
    @Operation(summary = "商品详情")
    @GetMapping("item/{skuId}")
    public Result item(@PathVariable Long skuId){
         ProductItemVo productItemVo = productService.item(skuId);
         return Result.build(productItemVo,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "分页查询")
    @GetMapping(value = "/{page}/{limit}")
    public Result list(@PathVariable Integer page,
                       @PathVariable Integer limit,
                       ProductSkuDto productSkuDto) {
        PageInfo<ProductSku> pageInfo = productService.findByPage(page, limit, productSkuDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //远程调用: 根据skuId返回sku信息
    @GetMapping("/getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable Long skuId){
        ProductSku productSku= productService.getBySkuId(skuId);
        return productSku;
    }

}
