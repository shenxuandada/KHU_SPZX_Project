package com.atguigu.spzx.manger.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ClassName: SysRoleUserMapper
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/5 下午7:17
 * Version 1.8
 */

@Mapper
public interface SysRoleUserMapper {
    //1 根据userId删除用户之前分配角色数据
    void deleteByUserId(Long userId);

    //2 重新分配信数据
    void doAssign(Long userId, Long roleId);

    //根据userId查询用户分配过的角色id列表
    List<Long> selectRoleIdsByUserId(Long userId);
}
