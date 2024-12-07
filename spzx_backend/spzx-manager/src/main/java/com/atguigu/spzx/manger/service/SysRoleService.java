package com.atguigu.spzx.manger.service;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * ClassName: SysRoleService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/1 下午4:40
 * Version 1.8
 */
public interface SysRoleService {

    //角色列表的方法
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit);

    void saveSysRole(SysRole sysRole);

    //3 角色修改的方法
    void updateSysRole(SysRole sysRole);

    //4 角色删除的方法
    void deleteById(Long roleId);

    //查询所有角色
    Map<String, Object> findAll(Long userId);
}
