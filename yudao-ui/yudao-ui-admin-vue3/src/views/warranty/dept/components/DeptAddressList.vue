<template>
  <!-- 列表 -->
  <ContentWrap>
    <el-button
      type="primary"
      plain
      @click="openForm('create')"
      v-hasPermi="['warranty:dept:create']"
    >
      <Icon icon="ep:plus" class="mr-5px" /> 新增
    </el-button>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="地址编号" align="center" prop="id" />
       <el-table-column label="地址类型" align="center" prop="addressType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.WARRANTY_DEPT_ADDRESS_TYPE" :value="scope.row.addressType" />
        </template>
      </el-table-column>
      <el-table-column label="街道地址" align="center" prop="streetAddress" />
      <el-table-column label="国家" align="center" prop="country" />
      <el-table-column label="省份/州" align="center" prop="stateProvince" />
      <el-table-column label="城市" align="center" prop="city" />
      <el-table-column label="区县" align="center" prop="district" />
      <el-table-column label="邮政编码" align="center" prop="postalCode" />
      <el-table-column label="是否默认地址" align="center" prop="isDefault">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.INFRA_BOOLEAN_STRING" :value="scope.row.isDefault" />
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['warranty:dept:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['warranty:dept:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>
    <!-- 表单弹窗：添加/修改 -->
    <DeptAddressForm ref="formRef" @success="getList" />
</template>
<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import { DeptApi } from '@/api/warranty/dept'
import DeptAddressForm from './DeptAddressForm.vue'
import { DICT_TYPE } from '@/utils/dict'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const props = defineProps<{
  deptId?: number // 部门id（主表的关联字段）
}>()
const loading = ref(false) // 列表的加载中
const list = ref([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  deptId: undefined as unknown
})

/** 监听主表的关联字段的变化，加载对应的子表数据 */
watch(
  () => props.deptId,
  (val: number) => {
    if (!val) {
      return
    }
    queryParams.deptId = val
    handleQuery()
  },
    { immediate: true, deep: true }
)

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await DeptApi.getDeptAddressPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  if (!props.deptId) {
    message.error('请选择一个部门')
    return
  }
  formRef.value.open(type, id, props.deptId)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await DeptApi.deleteDeptAddress(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
</script>