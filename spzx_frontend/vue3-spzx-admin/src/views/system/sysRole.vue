<template>
    <div class="search-div">
        <!-- 搜索表单 -->
        <el-form label-width="70px" size="small">
            <el-form-item label="역할 이름">
                <el-input
                        v-model="queryDto.roleName"
                        style="width: 100%"
                        placeholder="역할 이름"
                        ></el-input>
            </el-form-item>
            <el-row style="display:flex">
                <el-button type="primary" size="small" @click="searchSysRole">
                    검색
                </el-button>
                <el-button size="small" @click="resetData">초기화</el-button>
            </el-row>
        </el-form>

        <!-- 添加按钮 -->
        <div class="tools-div">
            <el-button type="success" size="small" @click="addShow">추가</el-button>
        </div>


        <!-- 添加角色表单对话框 -->
        <el-dialog v-model="dialogVisible" title="역할 추가 또는 수정" width="30%">
            <el-form label-width="120px">
                <el-form-item label="역할 이름">
                    <el-input v-model="sysRole.roleName"/>
                </el-form-item>
                <el-form-item label="역할 코드">
                    <el-input  v-model="sysRole.roleCode"/>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submit">제출</el-button>
                    <el-button @click="dialogVisible = false">취소</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        
        <!--- 角色表格数据 -->
        <el-table :data="list" style="width: 100%">
            <el-table-column prop="roleName" label="역할 이름" width="180" />
            <el-table-column prop="roleCode" label="역할 코드" width="180" />
            <el-table-column prop="createTime" label="생성 시간" />
            <el-table-column label="작업" align="center" width="280" #default="scope">
                <el-button type="primary" size="small" @click="editShow(scope.row)">
                    수정
                </el-button>
                <el-button type="danger" size="small" @click="deleteById(scope.row)">
                    삭제
                </el-button>

                <el-button type="warning" size="small" @click="showAssignMenu(scope.row)">
                    메뉴 할당
                </el-button>
            </el-table-column>
        </el-table>

<!-- 分配菜单的对话框 
// tree组件添加ref属性，后期方便进行tree组件对象的获取
-->
        <el-dialog v-model="dialogMenuVisible" title="메뉴 할당" width="40%">
            <el-form label-width="80px">
                <el-tree
                        :data="sysMenuTreeList"
                        ref="tree"   
                        show-checkbox
                        default-expand-all
                        :check-on-click-node="true"
                        node-key="id"
                        :props="defaultProps"
                />
                <el-form-item>
                    <el-button type="primary" @click="doAssign">제출</el-button>
                    <el-button @click="dialogMenuVisible = false">취소</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!--分页条-->
        <el-pagination
                    v-model:current-page="pageParams.page"
                    v-model:page-size="pageParams.limit"
                    :page-sizes="[10, 20, 50, 100]"
                    @size-change="fetchData"
                    @current-change="fetchData"
                    layout="total, sizes, prev, pager, next"
                    :total="total"
        />
  </div>

</template>

<script setup>
import {ref,onMounted} from 'vue'
import {DoAssignMenuIdToSysRole,GetSysRoleMenuIds,GetSysRoleListByPage,SaveSysRole,UpdateSysRole,DeleteSysRole} from '@/api/sysRole'
import {ElMessage,ElMessageBox } from 'element-plus'

/////////分配菜单
const defaultProps = {
  children: 'children',
  label: 'title',
}
const dialogMenuVisible = ref(false)
const sysMenuTreeList = ref([])

// 树对象变量
const tree = ref() 

// 默认选中的菜单数据集合
let roleId = ref()
const showAssignMenu = async row => { 
  dialogMenuVisible.value = true
  roleId.value = row.id
  const { data } = await GetSysRoleMenuIds(row.id)   // 请求后端地址获取所有的菜单数据，以及当前角色所对应的菜单数据
  sysMenuTreeList.value = data.sysMenuList
  tree.value.setCheckedKeys(data.roleMenuIds)   // 进行数据回显
}

const doAssign = async () => {
    const checkedNodes = tree.value.getCheckedNodes() ; // 获取选中的节点
    const checkedNodesIds = checkedNodes.map(node => {  // 获取选中的节点的id
        return {
            id: node.id,
            isHalf: 0
        }
    })
        
    // 获取半选中的节点数据，当一个节点的子节点被部分选中时，该节点会呈现出半选中的状态
    const halfCheckedNodes = tree.value.getHalfCheckedNodes() ; 
    const halfCheckedNodesIds = halfCheckedNodes.map(node => {   // 获取半选中节点的id
        return {
            id: node.id,
            isHalf: 1
        }
    })
        
    // 将选中的节点id和半选中的节点的id进行合并
    const menuIds = [...checkedNodesIds , ...halfCheckedNodesIds]  
    console.log(menuIds);

    // 构建请求数据
    const assignMenuDto = {
        roleId: roleId,
        menuIdList: menuIds
    }
 
    // 发送请求
    await DoAssignMenuIdToSysRole(assignMenuDto) ;
    ElMessage.success('Success')
    dialogMenuVisible.value = false

} 
/////////角色删除
const deleteById=(row)=>{
    ElMessageBox.confirm('이 작업은 해당 기록을 영구적으로 삭제합니다. 계속하시겠습니까?', 'Warning', {
        confirmButtonText: '확인',
        cancelButtonText: '취소',
        type: 'warning',
    }).then(async () => {
        const{code} = await DeleteSysRole(row.id)
        if(code === 200){
            ElMessage.success("Success")
            fetchData()
        }
    })
}


/////////角色添和修改
const roleForm = {
    id:"",
    roleName:"",
    roleCode:""
}
const sysRole = ref({
  id: '',
  roleName: '',
  roleCode: ''
})
//弹框的设置 true弹出框
const dialogVisible = ref(false)


//弹出框数据回显
const editShow = (row)=>{
    //对象扩展运算符
    sysRole.value={...row}
    dialogVisible.value=true
}

//点击添加弹出框的方法
const addShow = ()=>{
    dialogVisible.value = true
}

//添加和修改的方法
//判断ysRole包含id值进行修改操作,不包含id进行添加操作
const submit = async() =>{
        if(!sysRole.value.id){//没有id,添加操作
            const{code} = await SaveSysRole(sysRole.value)
        if(code === 200){
            //关闭弹框
            dialogVisible.value = false
            //提示信息
            ElMessage.success("Success")
            //刷新页面
            fetchData()
        }

    }else{//有id,修改操作
        const {code} = await UpdateSysRole(sysRole.value)
        if(code==200){
            dialogVisible.value = false
            ElMessage.success("Success")
            fetchData()
        }

    }
   
}


/////////角色列表
//定义数据模型
let list  = ref([])  //角色列表
let total = ref(0)   //总记录数

//分页数据
const pageParamsForm = {
    page: 1, //当前页
    limit: 3,//每页记录数
}
const pageParams = ref(pageParamsForm)

const queryDto = ref({"roleName":""}) //条件封装数据

//钩子函数
onMounted(()=>{
    fetchData()
})

//操作方法:列表方法和搜索方法
//列表方法:axios请求调用接口得到列表数据
const fetchData = async ()=>{
    const {data,code,message} = await GetSysRoleListByPage(pageParams.value.page,pageParams.value.limit,queryDto.value)
    list.value = data.list
    total.value = data.total
}


//搜索方法
const searchSysRole = ()=>{
    fetchData()
}
</script>

<style scoped>

.search-div {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 3px;
  background-color: #fff;
}

.tools-div {
  margin: 10px 0;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 3px;
  background-color: #fff;
}

</style>