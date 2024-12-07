package com.atguigu.spzx.manger.service;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageInfo;

/**
 * ClassName: SysUserService
 * Description:
 *
 * @Author Refactoring
 * Create 2024/7/29 下午10:23
 * Version 1.8
 */
public interface SysUserService {

    //用户登录
    LoginVo login(LoginDto loginDto);

    ////获取当前登录用户信息
    SysUser getUserInfo(String token);

    //用户退出
    void logout(String token);

    //1 用户条件分页查询接口
    PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    //2 用户添加
    void saveSysUser(SysUser sysUser);

    //3 用户修改
    void updateSysUser(SysUser sysUser);

    //4 用户删除
    void deleteById(Long userId);

    //用户分配角色
    void doAssign(AssginRoleDto assginRoleDto);
}


