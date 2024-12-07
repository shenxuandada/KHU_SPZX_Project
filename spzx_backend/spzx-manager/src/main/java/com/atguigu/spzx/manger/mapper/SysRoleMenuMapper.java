package com.atguigu.spzx.manger.mapper;

import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: SysRoleMenuMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/7 下午2:33
 * Version 1.8
 */

@Mapper
public interface SysRoleMenuMapper {

    // 角色分配过的菜单id列表
    List<Long> findSysRoleMenuByRoleId(Long roleId);

    //删除角色分配菜单数据
    void deleteByRoleId(Long roleId);

    //保存分配数据
    void doAssign(AssginMenuDto assginMenuDto);

    //把父菜单isHalf半开状态1
    void updateSysRoleMenuIsHalf(Long menuId);
}
