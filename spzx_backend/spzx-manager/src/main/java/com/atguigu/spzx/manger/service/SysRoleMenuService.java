package com.atguigu.spzx.manger.service;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;

import java.util.Map;

/**
 * ClassName: SysRoleMenuService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/7 下午2:30
 * Version 1.8
 */
public interface SysRoleMenuService {
    //1 查询所有菜单 和 查询角色分配过菜单id列表
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);

    //分配菜单
    void doAssign(AssginMenuDto assginMenuDto);
}
