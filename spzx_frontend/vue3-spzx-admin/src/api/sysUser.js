import request from '@/utils/request'
import SysUser from '@/views/system/sysUser.vue'

const base_api = '/admin/system/sysUser'

// 用户列表
export const GetSysUserListByPage = (current,limit,queryDto) => {
  return request({
    url: `${base_api}/findByPage/${current}/${limit}`, //路径
    method: 'get', //提交方法
    //接口@RequestBody 前端data:名称,以json格式传输
    //接口没有注释 ,前端params:名称
    params : queryDto, //其它参数
  })
}


//用户添加
export const SaveSysUser = (SysUser) =>{
    return request({
        url: `${base_api}/saveSysUser`, //路径
        method: 'post', //提交方法
        data : SysUser, //其它参数
      })
}

//用户修改
export const UpdateSysUser = (SysUser)=>{
    return request({
        url: `${base_api}/updateSysUser`, //路径
        method: 'put', //提交方法
        data : SysUser , //其它参数
      })
}



//用户删除
export const DeleteSysUser = (userId)=>{
    return request({
        url: `${base_api}/deleteById/${userId}`, //路径
        method: 'delete', //提交方法
      })
}

// 给用户分配角色请求
export const DoAssignRoleToUser = (assginRoleVo) => {
  return request({
      url: "/admin/system/sysUser/doAssign",
      method: 'post',
      data: assginRoleVo
  })
}