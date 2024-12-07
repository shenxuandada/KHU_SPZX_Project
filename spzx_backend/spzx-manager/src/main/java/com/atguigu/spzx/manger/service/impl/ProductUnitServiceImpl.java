package com.atguigu.spzx.manger.service.impl;

import com.atguigu.spzx.manger.mapper.ProductUnitMapper;
import com.atguigu.spzx.manger.service.ProductUnitService;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ProductUnitServiceImpl
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/10 下午6:13
 * Version 1.8
 */

@Service
public class ProductUnitServiceImpl implements ProductUnitService {

    @Autowired
    private ProductUnitMapper productUnitMapper;

    @Override
    public List<ProductUnit> findAll() {
        return productUnitMapper.findAll();
    }
}
