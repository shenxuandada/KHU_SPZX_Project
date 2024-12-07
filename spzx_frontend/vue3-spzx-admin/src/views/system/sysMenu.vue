<template>
    <div class="tools-div">
        <el-button type="success" size="small" @click="addShow">添 加</el-button>
    </div>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="30%">
        <el-form label-width="120px">
            <el-form-item label="메뉴 제목">
                <el-input v-model="sysMenu.title"/>
            </el-form-item>
            <el-form-item label="라우팅 이름">
                <el-input v-model="sysMenu.component"/>
            </el-form-item>
            <el-form-item label="정렬" >
                <el-input v-model="sysMenu.sortValue"/>
            </el-form-item>
            <el-form-item label="상태">
                <el-radio-group v-model="sysMenu.status">
                    <el-radio :label="1">정상</el-radio>
                    <el-radio :label="0">비활성화</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="saveOrUpdate">제출</el-button>
                <el-button @click="dialogVisible = false">취소</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
    <el-table
        :data="list"
        style="width: 100%; margin-bottom: 20px"
        row-key="id"
        border
        default-expand-all
    >
    <el-table-column prop="title" label="메뉴 제목" />
    <el-table-column prop="component" label="라우팅 이름" />
    <el-table-column prop="sortValue" label="정렬" />
    <el-table-column prop="status" label="상태" #default="scope">
      {{ scope.row.status == 1 ? '정상' : '비활성화' }}
    </el-table-column>
    <el-table-column prop="createTime" label="생성 시간" />

    <el-table-column label="작업" align="center" width="280" #default="scope" >
        <el-button type="success" size="small" @click="addShow(scope.row)">
          하위 노드 추가
        </el-button>
        <el-button type="primary" size="small" @click="editShow(scope.row)">
            수정
        </el-button>
        <el-button type="danger" size="small" @click="remove(scope.row.id)">
            삭제
        </el-button>
    </el-table-column>
  </el-table>
</template>

<script setup>
//引入调用的方法
import { ref , onMounted } from "vue"
import { FindNodes , SaveMenu , UpdateSysMenuById ,RemoveSysMenuById } from '@/api/sysMenu'
import { ElMessage, ElMessageBox } from 'element-plus'

// 定义表格数据模型
const list = ref([])

// 定义添加表单菜单表单相关数据模型
const dialogTitle = ref('추가')
const dialogVisible = ref(false)

//页面表单数据
const defaultForm = {
    id: '',
    parentId: 0,
    title: '',
    url: '',
    component: '',
    icon: '',
    sortValue: 1,
    status: 1,
}
// 表单响应式数据模型对象
const sysMenu = ref(defaultForm)

//=======================加载数据=========================
onMounted(() => {
    fetchData()
})

//=======================添加和修改功能====================
//进入添加
const addShow = (row) => {
  sysMenu.value = {}
  dialogVisible.value = true
  if(!row.id) {
    dialogTitle.value = '추가'
  }else {
    dialogTitle.value = '하위 노드 추가'
    sysMenu.value.parentId = row.id
  }
}

//进入修改
const editShow = row => {
  sysMenu.value = row
  dialogVisible.value = true
}

//提交保存与修改
const saveOrUpdate = () => {
    if (!sysMenu.value.id) {
        if(!sysMenu.value.parentId) {
            sysMenu.value.parentId = 0
        }
        saveData()
    }  else {
        updateData()
    }
}

// 修改
const updateData = async () => {
  await UpdateSysMenuById(sysMenu.value)
  dialogVisible.value = false
  ElMessage.success('Success')
  fetchData()
}

// 新增
const saveData = async () => {
  await SaveMenu(sysMenu.value)
  dialogVisible.value = false
  ElMessage.success('Success')
  fetchData()
}

//=======================分页列表====================
const fetchData = async () => {
  const { code, data, message } = await FindNodes()
  list.value = data
}

//=======================删除功能====================
const remove = async id => {
  console.log('removeDataById:' + id)
  ElMessageBox.confirm('이 작업은 해당 기록을 영구적으로 삭제합니다. 계속하시겠습니까?', 'Warning', {
    confirmButtonText: '확인',
    cancelButtonText: '취소',
    type: 'warning',
  }).then(async () => {
      const { code , message } = await RemoveSysMenuById(id)
      if(code === 200) {
        ElMessage.success('Success')
        fetchData()
      }else {
        ElMessage.error(message)
      }     
    })
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