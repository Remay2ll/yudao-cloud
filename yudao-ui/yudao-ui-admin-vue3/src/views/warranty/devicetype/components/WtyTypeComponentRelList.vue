<template>
  <!-- 列表 -->
  <ContentWrap>
    <el-button
      type="primary"
      plain
      @click="openForm('create')"
      v-hasPermi="['warranty:wty-device-type:create']"
    >
      <Icon icon="ep:plus" class="mr-5px" /> 新增
    </el-button>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column type="index" width="50" />
      <el-table-column label="配件" align="center" prop="componentName" />
      <el-table-column label="配件数量" align="center" prop="quantity" />
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
            v-hasPermi="['warranty:wty-device-type:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['warranty:wty-device-type:delete']"
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
  <WtyTypeComponentRelForm ref="formRef" @success="getList" />
</template>
<script setup lang="ts">
import { dateFormatter } from '@/utils/formatTime'
import { WtyDeviceTypeApi } from '@/api/warranty/devicetype'
import WtyTypeComponentRelForm from './WtyTypeComponentRelForm.vue'

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const props = defineProps<{
  typeId?: number // 设备类型ID（主表的关联字段）
}>()
const loading = ref(false) // 列表的加载中
const list = ref([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  typeId: undefined as unknown
})

/** 监听主表的关联字段的变化，加载对应的子表数据 */
watch(
  () => props.typeId,
  (val: number) => {
    if (!val) {
      return
    }
    queryParams.typeId = val
    handleQuery()
  },
  { immediate: true, deep: true }
)

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await WtyDeviceTypeApi.getWtyTypeComponentRelPage(queryParams)
    console.log('data', data)
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
  if (!props.typeId) {
    message.error('请选择一个设备类型')
    return
  }
  formRef.value.open(type, props.typeId, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await WtyDeviceTypeApi.deleteWtyTypeComponentRel(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}
</script>
