<template>
  <div class="search-div">
      <el-form label-width="70px" size="small">
          <el-row>
              <el-col :span="12">
                  <el-form-item label="브랜드">
                      <el-select
                              class="m-2"
                              placeholder="브랜드 선택"
                              size="small"
                              style="width: 100%"
                              v-model="queryDto.brandId"
                              >
                          <el-option
                                  v-for="item in brandList"
                                  :key="item.id"
                                  :label="item.name"
                                  :value="item.id"
                                  />
                      </el-select>
                  </el-form-item>
              </el-col>
              <el-col :span="12">
                  <el-form-item label="분류">
                      <el-cascader
                                  :props="categoryProps"
                                  style="width: 100%"
                                  v-model="searchCategoryIdList"
                                  />
                  </el-form-item>
              </el-col>
          </el-row>
          <el-row style="display:flex">
              <el-button type="primary" size="small" @click="fetchData()">
                  검색
              </el-button>
              <el-button size="small" @click="resetData">초기화</el-button>
          </el-row>
      </el-form>
  </div>

  <div class="tools-div">
      <el-button type="success" size="small" @click="addShow">추가</el-button>
  </div>
  <el-dialog v-model="dialogVisible" title="추가 또는 수정" width="30%">
        <el-form label-width="120px">
            <el-form-item label="브랜드">
                <el-select
                        class="m-2"
                        placeholder="브랜드 선택"
                        size="small"
                        v-model="categoryBrand.brandId"
                        >
                    <el-option
                            v-for="item in brandList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                            />
                </el-select>
            </el-form-item>
            <el-form-item label="분류">
                <el-cascader
                            :props="categoryProps"
                            v-model="categoryBrand.categoryId"
                            />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="saveOrUpdate">제출</el-button>
                <el-button @click="dialogVisible = false">취소</el-button>
            </el-form-item>
        </el-form>
</el-dialog>

  <el-table :data="list" style="width: 100%">
      <el-table-column prop="categoryName" label="분류" />
      <el-table-column prop="brandName" label="브랜드" />
      <el-table-column prop="logo" label="브랜드 아이콘" #default="scope">
          <img :src="scope.row.logo" width="50" />
      </el-table-column>
      <el-table-column prop="createTime" label="생성 시간" />
      <el-table-column label="작업" align="center" width="200" #default="scope" >
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
</template>

<script setup>
import { ref , onMounted } from 'vue'
import { FindAllBrand } from '@/api/brand.js'
import { FindCategoryByParentId } from '@/api/category.js'
import { GetCategoryBrandPageList,SaveCategoryBrand,UpdateCategoryBrandById,DeleteCategoryBrandById} from '@/api/categoryBrand.js'
import { ElMessage, ElMessageBox } from 'element-plus'  


////////////////////////////////////////////////////
//删除
const remove = async id => {
  ElMessageBox.confirm('이 작업은 해당 기록을 영구적으로 삭제합니다. 계속하시겠습니까?', 'Warning', {
    confirmButtonText: '확인',
    cancelButtonText: '취소',
    type: 'warning',
  })
    .then(async () => {
      await DeleteCategoryBrandById(id)
      ElMessage.success('삭제 성공')
      fetchData()
    })
    .catch(() => {
      ElMessage.info('삭제 취소')
    })
}

////////////////////////////////////////////////////修改
//进入修改
const editShow = row => {
  categoryBrand.value = row
  dialogVisible.value = true
}
//////////////////////////////////////////////////////







///////////////////////////////////////////////////添加
const defaultForm = {       //页面表单数据
  id: '',
  brandId: '',
  categoryId: '',
}
const categoryBrand = ref(defaultForm)    

const dialogVisible = ref(false)

//进入添加
const addShow = () => {
    categoryBrand.value = {}
    dialogVisible.value = true
}

//提交保存与修改
const saveOrUpdate = () => {
  if (categoryBrand.value.brandId == '') {
    ElMessage.info('브랜드 정보는 반드시 선택해야 합니다')
    return
  }
  //categoryId为数组：[1,2,3]
  if (categoryBrand.value.categoryId.length != 3) {
    ElMessage.info('분류 정보는 반드시 선택해야 합니다')
    return
  }
  //系统只需要三级分类id
  categoryBrand.value.categoryId = categoryBrand.value.categoryId[2]
  if (!categoryBrand.value.id) {
    saveData()
  } else {
    updateData() 
  }
}

// 修改
const updateData = async () => {
  await UpdateCategoryBrandById(categoryBrand.value)
  dialogVisible.value = false
  ElMessage.success('작업 성공')
  fetchData() 
}

// 新增
const saveData = async () => {
  await SaveCategoryBrand(categoryBrand.value)
  dialogVisible.value = false
  ElMessage.success('작업 성공')
  fetchData()
}

///////////////////////////////////////////////////列表
const props = {
lazy: true,
value: 'id',
label: 'name',
leaf: 'leaf',
async lazyLoad(node, resolve) {   // 加载数据的方法
  if (typeof node.value == 'undefined') node.value = 0
  const { data } = await FindCategoryByParentId(node.value)
  data.forEach(function(item) {       //hasChildren判断是否有子节点
    item.leaf = !item.hasChildren
  })
  resolve(data)  // 返回数据  
}
};

const categoryProps = ref(props)

//查询所有品牌
const selectAllBrandList = async () => {
  const { data } = await FindAllBrand()
  brandList.value = data
}

// 定义搜索表单数据模型
const brandList = ref([])
// 定义表格数据模型
const list = ref([])
// 分页条数据模型
const total = ref(0)
// 搜索表单数据模型
const queryDto = ref({ brandId: '', categoryId: '' })
const searchCategoryIdList = ref([])
//分页条数据模型
const pageParamsForm = {
page: 1,   // 页码
limit: 2, // 每页记录数
}
const pageParams = ref(pageParamsForm)    
  
// onMounted钩子函数
onMounted(() => {
  selectAllBrandList() // 查询所有的品牌数据
  fetchData()
})
  
//重置
const resetData = () => {
queryDto.value = { brandId: '', categoryId: '' }
searchCategoryIdList.value = []
fetchData()
}

//分页变化
const handleSizeChange = size => {
pageParams.value.limit = size
fetchData()
}
const handleCurrentChange = number => {
pageParams.value.page = number
fetchData()
}

// 分页列表查询
const fetchData = async () => {
if (searchCategoryIdList.value.length == 3) {
   queryDto.value.categoryId = searchCategoryIdList.value[2]
}
const { data } = await GetCategoryBrandPageList( pageParams.value.page, pageParams.value.limit, queryDto.value)
list.value = data.list
total.value = data.total
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