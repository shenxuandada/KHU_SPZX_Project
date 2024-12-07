package com.atguigu.spzx.manger.service.impl;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manger.mapper.SysMenuMapper;
import com.atguigu.spzx.manger.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manger.mapper.SysUserMapper;
import com.atguigu.spzx.manger.service.SysMenuService;
import com.atguigu.spzx.manger.utils.MenuHelper;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.ehcache.core.util.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * ClassName: SysMenuServiceImpl
 * Description:
 *
 * @Author Refactoring
 * Create 2024/8/6 下午5:54
 * Version 1.8
 */

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    //菜单列表
    @Override
    public List<SysMenu> findNodes() {

        //1 查询所有菜单,返回list集合
        List<SysMenu> sysMenuList =  sysMenuMapper.findAll();
        if(CollectionUtils.isEmpty(sysMenuList)){
            return null;
        }

        //2 调用工具类的方法,把返回list集合封装要求数据格式
        List<SysMenu>treeList = MenuHelper.buildTree(sysMenuList);
        return treeList;
    }

    //菜单添加
    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.save(sysMenu);

        //新添加子菜单,把父菜单isHalf半开状态1
        updateSysRoleMenu(sysMenu);
    }

    //新添加子菜单,把父菜单isHalf半开状态1
    private void updateSysRoleMenu(SysMenu sysMenu) {
        //获取当前添加菜单的父菜单
        SysMenu parentMenu= sysMenuMapper.selectParentMenu(sysMenu.getParentId());
        if(parentMenu != null){
            //把父菜单isHalf半开状态1
            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId());
            //调用递归
            updateSysRoleMenu(parentMenu);
        }
    }

    //菜单修改
    @Override
    public void update(SysMenu sysMenu) {
        sysMenuMapper.update(sysMenu);
    }

    //菜单删除
    @Override
    public void removeById(Long id) {
        //根据当前菜单id,查询是否存在子菜单
        int count = sysMenuMapper.selectCountById(id);

        //判断,count大于0,包含子菜单
        if(count > 0){
            throw new GuiguException(ResultCodeEnum.NODE_ERROR);
        }
        //count =0直接删除
        sysMenuMapper.delete(id);
    }

    //查询用户可以操作菜单
    @Override
    public List<SysMenuVo> findMenusByUserId() {
        //获取当前用户id
        SysUser sysUser = AuthContextUtil.get();
        Long userId = sysUser.getId();

        //根据userId查询可以操作菜单
        //封装要求数据格式,返回
        List<SysMenu> sysMenuList = MenuHelper.buildTree(sysMenuMapper.findMenusByUserId(userId));
        List<SysMenuVo>sysMenuVos = this.buildMenus(sysMenuList);
        return sysMenuVos;
    }

    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}
