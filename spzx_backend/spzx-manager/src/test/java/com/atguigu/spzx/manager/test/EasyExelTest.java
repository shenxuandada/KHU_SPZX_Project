package com.atguigu.spzx.manager.test;

import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import net.bytebuddy.asm.MemberSubstitution;
import org.checkerframework.checker.units.qual.C;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: EasyExelTest
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/8 下午4:41
 * Version 1.8
 */
public class EasyExelTest {
    public static void main(String[] args) {
//        read();
        write();
    }

    //读操作
    public static void read() {

        //1 定义读取excel文件位置
        String fileName = "C://Users//hp//Desktop//01.xlsx";
        //2 调用方法
        ExcelListener excelListener = new ExcelListener();
        EasyExcel.read(fileName, CategoryExcelVo.class, excelListener)
                .sheet().doRead();
        List<CategoryExcelVo> data = excelListener.getData();
        System.out.println(data);
    }

    //写操作
    public static void write() {
        List<CategoryExcelVo> list = new ArrayList<>();
        list.add(new CategoryExcelVo(1L , "数码办公" , "",0L, 1, 1)) ;
        list.add(new CategoryExcelVo(11L , "华为手机" , "",1L, 1, 2)) ;
        EasyExcel.write("C://Users//hp//Desktop//02.xlsx", CategoryExcelVo.class)
                .sheet("分类数据").doWrite(list);
    }
}
