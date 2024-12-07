package com.atguigu.spzx.manger.service.impl;

import com.atguigu.spzx.manger.mapper.SysRoleMapper;
import com.atguigu.spzx.manger.mapper.SysRoleUserMapper;
import com.atguigu.spzx.manger.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: SysRoleServiceImpl
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/1 下午4:40
 * Version 1.8
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {


    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    // 角色列表的方法
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        // 设置分页参数
        PageHelper.startPage(current,limit);

        // 根据条件查询所有数据
        List<SysRole>list =  sysRoleMapper.findByPage(sysRoleDto);

        //封装pageInfo对象
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //2 角色添加的方法
    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.save(sysRole);
    }

    //3 角色修改的方法
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.update(sysRole);

    }

    //4 角色删除的方法
    @Override
    public void deleteById(Long roleId) {

        sysRoleMapper.delete(roleId);
    }

    @Override
    public Map<String, Object> findAll(Long userId) {
        //1 查询所有角色
        List<SysRole>roleList = sysRoleMapper.findAll();

        //2 分配过的角色列表
        //根据userId查询用户分配过的角色id列表
        List<Long> roleIds = sysRoleUserMapper.selectRoleIdsByUserId(userId);

        Map<String, Object> map = new HashMap<>();
        map.put("allRolesList",roleList);
        map.put("sysUserRoles",roleIds);

        return map;
    }
}
