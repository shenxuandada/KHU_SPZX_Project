package com.atguigu.spzx.manger.service;

import com.atguigu.spzx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * ClassName: CategoryService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/7 下午5:32
 * Version 1.8
 */
public interface CategoryService {

    //分类列表,每次只查询一层数据
    List<Category> findCategoryList(Long id);

    //导出
    void exportData(HttpServletResponse response);

    //导入
    void importData(MultipartFile file);
}
