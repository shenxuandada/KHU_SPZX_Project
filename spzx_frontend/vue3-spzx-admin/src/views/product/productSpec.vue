<template>
    <div class="tools-div">
        <el-button type="success" size="small" @click="addShow">추가</el-button>
    </div>

    <el-table :data="list" style="width: 100%">
        <el-table-column prop="specName" label="규격 이름" />
        <el-table-column label="규격 값" #default="scope">
        <div
            v-for="(item1, index1) in scope.row.specValue"
            :key="index1"
            style="padding: 5px; margin: 0;width: 100%;"
        >
            {{ item1.key }}：
            <span
            v-for="(item2, index2) in item1.valueList"
            :key="index2"
            class="div-atrr"
            >
            {{ item2 }}
            </span>
        </div>
        </el-table-column>
        <el-table-column prop="createTime" label="생성 시간" />
        <el-table-column label="작업" align="center" width="200" #default="scope">
            <el-button type="primary" size="small" @click="editShow(scope.row)">
                수정
            </el-button>
            <el-button type="danger" size="small" @click="remove(scope.row.id)">
                삭제
            </el-button>
        </el-table-column>
    </el-table>

    <el-pagination
               v-model:current-page="pageParams.page"
               v-model:page-size="pageParams.limit"
               :page-sizes="[10, 20, 50, 100]"
               layout="total, sizes, prev, pager, next"
               :total="total"
               @size-change="handleSizeChange"
               @current-change="handleCurrentChange"
            />
        <el-dialog v-model="dialogVisible" title="추가 또는 수정" width="40%">
            <el-form label-width="120px">
                <el-form-item label="규격 이름">
                    <el-input v-model="productSpec.specName"/>
                </el-form-item>
                <el-form-item>
                    <el-button size="default" type="success" @click="addSpec">
                        새 사양 추가
                    </el-button>
                </el-form-item>
                <el-form-item label="" v-for="(item , index) in productSpec.specValue" :key="index">
                    <el-row>
                        <el-col :span="10">
                            <el-input
                                    v-model="item.key"
                                    placeholder="규격"
                                    style="width: 90%;"
                                    />
                        </el-col>
                        <el-col :span="10">
                            <el-input
                                    v-model="item.valueList"
                                    placeholder="규격 값(예: 흰색, 빨간색)"
                                    style="width: 90%;"
                                    />
                        </el-col>
                        <el-col :span="4">
                            <el-button size="default" type="danger" @click="delSpec(index)">삭제</el-button>
                        </el-col>
                    </el-row>  
                </el-form-item>
                <el-form-item align="right">
                    <el-button type="primary" @click="saveOrUpdate">제출</el-button>
                    <el-button @click="dialogVisible = false">취소</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
</template>

<script setup>
import { ref , onMounted } from 'vue'
import { DeleteProductSpecById,GetProductSpecPageList,SaveProductSpec,UpdateProductSpecById } from '@/api/productSpec.js'
import { ElMessage, ElMessageBox } from 'element-plus'

// 表格数据模型
const list = ref([ 
    {
        "id": 3,
        "createTime": "2023-05-06 12:56:08",
        "specName": "三星笔记本电脑",
        "specValue": [{"key":"内存","valueList":["8G","18G","32G"]}]
    },
    {
        "id": 2,
        "createTime": "2023-05-06 12:56:08",
        "specName": "笔记本电脑",
        "specValue": [{"key":"内存","valueList":["8G","18G","32G"]}]
    },
    {
        "id": 1,
        "createTime": "2023-05-06 12:40:22",
        "specName": "Xiaomi",
        "specValue": [{"key":"颜色","valueList":["白色","红色","黑色"]},{"key":"内存","valueList":["8G","18G"]}]
    }
    
])

// 分页条数据模型
const total = ref(0)

//分页条数据模型
const pageParamsForm = {
  page: 1, // 页码
  limit: 10, // 每页记录数
}
const pageParams = ref(pageParamsForm)

// 定义数据模型
const dialogVisible = ref(false)

// 添加表单数据模型
const defaultForm = {
  id: '',
  specName: '',
  specValue: [
        {
            "key": "颜色",
            "valueList": [
                "白色",
                "红色",
                "黑色"
            ]
        },
        {
            "key": "内存",
            "valueList": [
                "8G",
                "18G"
            ]
        }
    ]
}
const productSpec = ref(defaultForm)

//进入修改
const editShow = row => {
    productSpec.value = row 
    dialogVisible.value = true
}

//进入添加
const addShow = () => {
  dialogVisible.value = true
  productSpec.value = {
    id: '',
    specName: '',
    specValue: []
  }
}

// 页面添加规格操作
const addSpec = () => {
    productSpec.value.specValue.push({})
}

// 页面删除规格元素
const delSpec = (index) => {
    productSpec.value.specValue.splice(index , 1)
} 

// 提交表单
const saveOrUpdate = async () => {

    if(!productSpec.value.id)  {
        saveData()
    }else {
        updateData()
    }

}

// 保存修改
const updateData = async () => {

    // 需要将productSpec.value.specValue转换成json字符串提交到后端，通过clone一个新的对象进行实现
    const productSpecClone = JSON.parse(JSON.stringify(productSpec.value))

    // 将productSpecClone.specValue.valueList转换成数组，因为后端需要的数组格式的数据[{"key":"内存","valueList":["8G","18G","32G"]}]
    // v-model绑定的数据模型为字符串
    productSpecClone.specValue.forEach(item => {  
        console.log(typeof item.valueList)
        if(typeof item.valueList === 'string') {   // 针对规格数据修改完毕以后数据类型有可能会变成string，针对string类型的数据将其转换成数组
            item.valueList = item.valueList.split(",")
        }   
    })
    productSpecClone.specValue = JSON.stringify(productSpecClone.specValue)

    // 提交表单
    await UpdateProductSpecById(productSpecClone)

    dialogVisible.value = false
    ElMessage.success('Success')
    fetchData()
}

// 保存数据
const saveData = async () => {

    // 需要将productSpec.value.specValue转换成json字符串提交到后端，通过clone一个新的对象进行实现
    const productSpecClone = JSON.parse(JSON.stringify(productSpec.value))

    // 将productSpecClone.specValue.valueList转换成数组，因为后端需要的数组格式的数据[{"key":"内存","valueList":["8G","18G","32G"]}]
    // v-model绑定的数据模型为字符串
    productSpecClone.specValue.forEach(item => {     
        item.valueList = item.valueList.split(",")
    })
    productSpecClone.specValue = JSON.stringify(productSpecClone.specValue)
    
    console.log(productSpecClone);

    // 提交表单
    await SaveProductSpec(productSpecClone)
    
    dialogVisible.value = false
    ElMessage.success('Success')
    fetchData()
}

//删除
const remove = async id => {
  ElMessageBox.confirm('이 작업은 해당 기록을 영구적으로 삭제합니다. 계속하시겠습니까?', 'Warning', {
    confirmButtonText: '확인',
    cancelButtonText: '취소',
    type: 'warning',
  })
    .then(async () => {
      await DeleteProductSpecById(id)
      ElMessage.success('Success')
      fetchData()
    })
    .catch(() => {
      ElMessage.info('삭제 취소')
    })
}

// 钩子函数
onMounted(()=> {
    fetchData()
})

//页面变化
const handleSizeChange = size => {
  pageParams.value.limit = size
  fetchData()
}
const handleCurrentChange = number => {
  pageParams.value.page = number
  fetchData()
}

// 分页查询
const fetchData = async () => {
   const { data } = await GetProductSpecPageList(pageParams.value.page , pageParams.value.limit) 
   data.list.forEach(item => {
      item.specValue = JSON.parse(item.specValue)       // 将规格字符串转换成规格js对象
   });
   list.value = data.list
   total.value = data.total
   
}
</script>

<style scoped>
.tools-div {
  margin: 10px 0;
  padding: 10px;
  border: 1px solid #ebeef5;
  border-radius: 3px;
  background-color: #fff;
}
    
.div-atrr {
  padding: 5px 10px;
  margin: 0 10px;
  background-color: powderblue;
  border-radius: 10px;
}

</style>