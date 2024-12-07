package com.atguigu.spzx.common.log.service;

import com.atguigu.spzx.model.entity.system.SysOperLog;

/**
 * ClassName: AsyncOperLogService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/11 下午7:59
 * Version 1.8
 */
public interface AsyncOperLogService {
    public abstract void saveSysOperLog(SysOperLog sysOperLog) ;
}
