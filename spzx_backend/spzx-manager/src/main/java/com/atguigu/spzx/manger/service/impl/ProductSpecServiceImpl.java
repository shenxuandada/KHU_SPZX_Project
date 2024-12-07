package com.atguigu.spzx.manger.service.impl;
import com.atguigu.spzx.manger.mapper.ProductSpecMapper;
import com.atguigu.spzx.manger.service.ProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * ClassName: ProductSpecServiceImpl
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/9 下午10:17
 * Version 1.8
 */

@Service
public class ProductSpecServiceImpl implements ProductSpecService {



    @Autowired
    private ProductSpecMapper productSpecMapper;


    //列表
    @Override
    public PageInfo<ProductSpec>findByPage(Integer page,Integer limit) {
        PageHelper.startPage(page,limit);  // 设置分页参数
        List<ProductSpec> list = productSpecMapper.findByPage();  // 查询数据
        return new PageInfo<>(list);  // 返回分页信息
    }

    //添加
    @Override
    public void save(ProductSpec productSpec) {
        productSpecMapper.save(productSpec);
    }

    //修改
    @Override
    public void updateById(ProductSpec productSpec) {
        productSpecMapper.update(productSpec);

    }

    //删除
    @Override
    public void deleteById(Long id) {
        productSpecMapper.delete(id);
    }

    //查询所有
    @Override
    public List<ProductSpec> findAll() {
        return productSpecMapper.findAll();
    }
}
