package com.atguigu.spzx.product.service.impl;

import com.alibaba.fastjson2.JSON;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.service.CategoryService;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * ClassName: CategoryServiceImpl
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/12 下午6:05
 * Version 1.8
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;



    // 查询所有一级分类
    @Override
    public List<Category> selectOneCategory() {
        //1 查询redis,是否有一级分类
        String categoryOneJson = redisTemplate.opsForValue().get("category:one");
        //category:one
        //2 如果redis包含所有一级分类,直接返回
        if (StringUtils.hasText(categoryOneJson)) {
            //categoryOneJson字符串转换成List<Category>
            List<Category> existcategoryList = JSON.parseArray(categoryOneJson, Category.class);
            return existcategoryList;
        }

        //3 如果redis没有所有一级分类,查询数据库,把数据库内容返回,并且查询内容放到redis中
        List<Category> categoryList = categoryMapper.selectOneCategory();
        //查询内容放到redis中
        redisTemplate.opsForValue().set("category:one"
                , JSON.toJSONString(categoryList)
                , 7, TimeUnit.DAYS);
        return categoryList;
    }


    //查询所有分类,树形封装
    //category::all
    @Cacheable(value = "category",key = "'all'")
    @Override
    public List<Category> findCategoryTree() {

        //1 查询所有分类, 返回list集合
        List<Category> allCategoryList = categoryMapper.findAll();

        //2 遍历所有分类list集合,通过 parentid=0,得到所有一级分类
        List<Category> oneCategoryList =
                allCategoryList.stream()
                        .filter(item -> item.getParentId().longValue() == 0)
                        .collect(Collectors.toList());

        //3 遍历所有一级分类,通过id = parentid,得到一级下面二级分类
        oneCategoryList.forEach(oneCategory -> {
            List<Category> twoCategoryList = allCategoryList.stream()
                    .filter(item -> item.getParentId() == oneCategory.getId())
                    .collect(Collectors.toList());
            // 把二级分类封装到一级分类里面
            oneCategory.setChildren(twoCategoryList);


            //4 遍历所有二级分类,通过id = parentid,得到二级下面三级分类
            twoCategoryList.forEach(twoCategory -> {
                List<Category> threeCategroyList = allCategoryList.stream().filter(item -> item.getParentId() == twoCategory.getId())
                        .collect(Collectors.toList());

                // 把三级分类封装到二级分类里面
                twoCategory.setChildren(threeCategroyList);
            });
        });
        return oneCategoryList;
    }
}
