<template>
    <!---搜索表单-->
    <div class="search-div">
        <el-form label-width="70px" size="small">
            <el-row>
                <el-col :span="12">
                    <el-form-item label="키워드">
                        <el-input v-model="queryDto.keyword"
                                style="width: 100%"
                                placeholder="사용자 이름"
                                ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="12">
                    <el-form-item label="생성 시간">
                        <el-date-picker v-model="createTimes"
                                        type="daterange"
                                        range-separator="To"
                                        start-placeholder="시작 시간"
                                        end-placeholder="종료 시간"
                                        format="YYYY-MM-DD"
                                        value-format="YYYY-MM-DD"
                                        />
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row style="display:flex">
                <el-button type="primary" size="small" @click="searchSysUser">
                    검색
                </el-button>
                <el-button size="small" @click="resetData">초기화</el-button>
            </el-row>
        </el-form>
    </div>  

    <!--添加按钮-->
    <div class="tools-div">
        <el-button type="success" size="small" @click="addShow">추가</el-button>
    </div>

    <el-dialog v-model="dialogVisible" title="추가 또는 수정" width="40%">
        <el-form label-width="120px">
            <el-form-item label="아이디">
                <el-input v-model="sysUser.userName"/>
            </el-form-item>
            <el-form-item v-if="sysUser.id == null" label="비밀번호">
                <el-input type="password" show-password v-model="sysUser.password"/>
            </el-form-item>
            <el-form-item label="성명">
                <el-input v-model="sysUser.name"/>
            </el-form-item>
            <el-form-item label="핸드폰">
                <el-input v-model="sysUser.phone"/>
            </el-form-item>
            <el-form-item label="프로필 사진">
                <el-upload
                            class="avatar-uploader"
                            action="http://localhost:8501/admin/system/fileUpload"
                            :show-file-list="false"
                            :on-success="handleAvatarSuccess"
                            :headers="headers"
                    >
                    <img v-if="sysUser.avatar" :src="sysUser.avatar" class="avatar" />
                    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
                </el-upload>
            </el-form-item>
            <el-form-item label="설명">
                <el-input  v-model="sysUser.description"/>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submit">제출</el-button>
                <el-button @click="dialogVisible = false">취소</el-button>
            </el-form-item>
        </el-form>
    </el-dialog> 

    <!---数据表格-->
    <el-table :data="list" style="width: 100%">
        <el-table-column prop="userName" label="사용자 이름" />
        <el-table-column prop="name" label="이름" />
        <el-table-column prop="phone" label="핸드폰" />
        <el-table-column prop="avatar" label="프로필 사진" #default="scope">
            <img :src="scope.row.avatar" width="50" />
        </el-table-column>
        <el-table-column prop="description" label="설명" />
        <el-table-column prop="status" label="상태" #default="scope">
            {{ scope.row.status == 1 ? '정상' : '비활성화' }}
        </el-table-column>
        <el-table-column prop="createTime" label="생성 시간" />
        <el-table-column label="작업" align="center" width="280" #default="scope" >
            <el-button type="primary" size="small" @click="editSysUser(scope.row)">
                수정
            </el-button>
            <el-button type="danger" size="small" @click="deleteById(scope.row)">
                삭제
            </el-button>
            <el-button type="warning" size="small" @click ="showAssignRole(scope.row)">
                역할 할당
            </el-button>
        </el-table-column>
    </el-table>

    <el-dialog v-model="dialogRoleVisible" title="역할 할당" width="40%">
        <el-form label-width="80px">
            <el-form-item label="사용자 이름">
                <el-input disabled :value="sysUser.userName"></el-input>
            </el-form-item>

            <el-form-item label="역할 목록">
                <el-checkbox-group v-model="userRoleIds">
                    <el-checkbox v-for="role in allRoles" :key="role.id" :label="role.id">
                        {{ role.roleName }}
                    </el-checkbox>
                </el-checkbox-group>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="doAssign">제출</el-button>
                <el-button @click="dialogRoleVisible = false">취소</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>

    <el-pagination
                v-model:current-page="pageParams.page"
                v-model:page-size="pageParams.limit"
                :page-sizes="[10, 20, 50, 100]"
                @size-change="fetchData"
                @current-change="fetchData"
                layout="total, sizes, prev, pager, next"
                :total="total"
                />

</template>

<script setup>
import { ref,onMounted } from 'vue';
import { DoAssignRoleToUser, GetSysUserListByPage,SaveSysUser,UpdateSysUser,DeleteSysUser} from '@/api/sysUser' 
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from 'axios';
import {GetAllRoleList} from '@/api/sysRole'

/////////////用户分配角色
// 角色列表
const userRoleIds = ref([])
const allRoles = ref([
    {"id":1 , "roleName":"관리자"},
    {"id":2 , "roleName":"영업 직원"},
    {"id":3 , "roleName":"상품 입력 담당자"},
])
const dialogRoleVisible = ref(false)
const showAssignRole = async row => {
  sysUser.value = {...row}
  dialogRoleVisible.value = true

  //得到所有角色
  const {data} = await GetAllRoleList(row.id)
  allRoles.value = data.allRolesList

  //用户分配过的角色
  userRoleIds.value = data.sysUserRoles

}


//分配角色
const doAssign =async ()=>{
    let assignRoleVo={
        userId: sysUser.value.id,
        roleIdList:userRoleIds.value
    }
    const{code} = await DoAssignRoleToUser(assignRoleVo)
    if(code === 200){
        ElMessage.success("작업 성공")
        dialogRoleVisible.value = false
        fetchData()
    }

}
/////////////上传
import { useApp } from '@/pinia/modules/app'

const headers = {
  token: useApp().authorization.token     // 从pinia中获取token，在进行文件上传的时候将token设置到请求头中
}

// 图像上传成功以后的事件处理函数
const handleAvatarSuccess = (response, uploadFile) => {
    sysUser.value.avatar = response.data
}



/////////////用户删除
const deleteById=(row)=>{
    ElMessageBox.confirm('이 작업은 해당 기록을 영구적으로 삭제합니다. 계속하시겠습니까?', 'Warning', {
        confirmButtonText: '확인',
        cancelButtonText: '취소',
        type: 'warning',
    }).then(async () => {
      const{code} = await DeleteSysUser(row.id)
      if(code === 200){
        ElMessage.success("Success")
        fetchData()
      }
    })
}


/////////////用户添加 和 修改
const dialogVisible = ref(false)

const from={
    userName:"",
    name: "" ,
    phone: "" ,
    password: "" ,
    description:"",
    avatar: ""
}
const sysUser = ref(from)

//点击修改按钮弹框,数据回显
const editSysUser=(row)=>{
    sysUser.value={...row}
    dialogVisible.value=true
}


//点击添加弹框
const addShow=()=>{
    sysUser.value={}
    dialogVisible.value=true
}


//提交方法
const submit = async()=> {
    if(!sysUser.value.id) {//没有id进行添加操作
        const {code} = await SaveSysUser(sysUser.value)
        if(code === 200){
          dialogVisible.value=false
          ElMessage.success("Success")
          fetchData()
        }
    }else{//修改
        const{code} = await UpdateSysUser(sysUser.value)
        if(code === 200){
          dialogVisible.value=false
          ElMessage.success("Success")
          fetchData()
        }
    }
}


/////////////用户列表
// 表格数据模型
const list = ref([]);

// 分页条数据模型
const total = ref(0)

//分页
const pageParamsForm = {
    page:1, //当前页
    limit:3 //每页记录数
}
const pageParams = ref(pageParamsForm)


//封装条件数据模型
const queryDto = ref({
    keywrod:"",
    createTimeBegin:"",
    createTimeEnd:""
})


//开始和结束时间模型
const createTimes = ref([])


//钩子函数
onMounted(()=>{
    fetchData()
})


//条件查询方法 axios调用
const fetchData = async ()=>{

//获取开始和结束时间
        if(createTimes.value.length == 2){
            queryDto.value.createTimeBegin =  createTimes.value[0]
            queryDto.value.createTimeEnd = createTimes.value[1]
        }
        const{data} = await GetSysUserListByPage(pageParams.value.page,pageParams.value.limit,queryDto.value)
        list.value = data.list
        total.value = data.total
}

//搜索方法  
const searchSysUser=()=>{
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
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}
.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}
.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>