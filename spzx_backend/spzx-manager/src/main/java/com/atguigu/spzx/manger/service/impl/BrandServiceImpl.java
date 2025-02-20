package com.atguigu.spzx.manger.service.impl;

import com.atguigu.spzx.manger.mapper.BrandMapper;
import com.atguigu.spzx.manger.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: BrandServiceImpl
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/8 下午9:26
 * Version 1.8
 */

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper ;

    //列表
    @Override
    public PageInfo<Brand> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Brand> list = brandMapper.findByPage();
        PageInfo<Brand> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //添加
    @Override
    public void save(Brand brand) {
        brandMapper.save(brand) ;
    }

    //修改
    @Override
    public void updateById(Brand brand) {
        brandMapper.updateById(brand) ;
    }

    //删除
    @Override
    public void deleteById(Long id) {
        brandMapper.deleteById(id) ;
    }

    //查询所有品牌
    @Override
    public List<Brand> findAll() {
        return brandMapper.findByPage();
    }
}
