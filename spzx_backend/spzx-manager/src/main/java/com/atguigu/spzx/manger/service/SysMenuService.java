package com.atguigu.spzx.manger.service;

import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;

import java.util.List;

/**
 * ClassName: SysMenuService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/6 下午5:54
 * Version 1.8
 */
public interface SysMenuService {
    //菜单列表
    List<SysMenu> findNodes();

    //菜单添加
    void save(SysMenu sysMenu);

    //菜单修改
    void update(SysMenu sysMenu);

    //菜单删除
    void removeById(Long id);

    //查询用户可以操作菜单
    List<SysMenuVo> findMenusByUserId();
}
