package com.atguigu.spzx.manger.controller;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.aspect.LogAspect;
import com.atguigu.spzx.manger.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ClassName: SysRoleController
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/1 下午4:38
 * Version 1.8
 */

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private LogAspect logAspect;

    //查询所有角色
    @GetMapping("/findAllRoles/{userId}")
    public Result findAllRoles(@PathVariable("userId") Long userId) {
        Map<String,Object> map =  sysRoleService.findAll(userId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }


    //4 角色删除的方法
    @DeleteMapping("/deleteById/{roleId}")
    public Result deleteById(@PathVariable("roleId") Long roleId) {
        sysRoleService.deleteById(roleId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    //3 角色修改的方法
    @PutMapping("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //2 角色添加的方法
    @Log(title = "角色管理:添加",businessType = 1)
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 角色列表方法
    // current:当前页 limit:每页显示记录数
    // SysRoleDto:条件角色名称对象
    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @RequestBody SysRoleDto sysRoleDto){
        //pageHelper插件实现分页
        PageInfo<SysRole>pageInfo = sysRoleService.findByPage(sysRoleDto,current,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);




    }
}
