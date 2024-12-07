package com.atguigu.spzx.manger.mapper;

import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * ClassName: SysOperLogMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/11 下午8:01
 * Version 1.8
 */
@Mapper
public interface SysOperLogMapper {

    //保存日志数据
    void insert(SysOperLog sysOperLog);
}
