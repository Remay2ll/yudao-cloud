<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="120px"
    >
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="工单编号" prop="workOrderNo">
        <el-input
          v-model="queryParams.workOrderNo"
          placeholder="请输入工单编号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="工单标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入工单标题"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="工单类型" prop="orderType">
        <el-select
          v-model="queryParams.orderType"
          placeholder="请选择工单类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.WARRANTY_ORDER_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="工单状态" prop="orderStatus">
        <el-select
          v-model="queryParams.orderStatus"
          placeholder="请选择工单状态"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.WARRANTY_ORDER_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="优先级" prop="priority">
        <el-select
          v-model="queryParams.priority"
          placeholder="请选择优先级"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.WARRANTY_ORDER_PRIORITY)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联维保协议号" prop="agreementNo">
        <el-input
          v-model="queryParams.agreementNo"
          placeholder="请输入关联维保协议号"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="客户名称" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="报修时间" prop="reportTime">
        <el-date-picker
          v-model="queryParams.reportTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="负责维保公司ID" prop="maintenanceCompanyId">
        <el-input
          v-model="queryParams.maintenanceCompanyId"
          placeholder="请输入负责维保公司ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="指派维修工姓名" prop="assignedToUserName">
        <el-input
          v-model="queryParams.assignedToUserName"
          placeholder="请输入指派维修工姓名"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="设备所属单位ID" prop="owningDeptId">
        <el-input
          v-model="queryParams.owningDeptId"
          placeholder="请输入设备所属单位ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="服务片区部门ID" prop="serviceAreaDeptId">
        <el-input
          v-model="queryParams.serviceAreaDeptId"
          placeholder="请输入服务片区部门ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="故障类型ID" prop="issueTypeId">
        <el-input
          v-model="queryParams.issueTypeId"
          placeholder="请输入故障类型ID"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="故障类型名称" prop="issueTypeName">
        <el-input
          v-model="queryParams.issueTypeName"
          placeholder="请输入故障类型名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['warranty:mainten-order:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['warranty:mainten-order:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="工单编号" align="center" prop="workOrderNo" />
      <el-table-column label="标题" align="center" prop="title" />
      <el-table-column label="工单类型" align="center" prop="orderType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.WARRANTY_ORDER_TYPE" :value="scope.row.orderType" />
        </template>
      </el-table-column>
      <el-table-column label="工单状态" align="center" prop="orderStatus">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.WARRANTY_ORDER_STATUS" :value="scope.row.orderStatus" />
        </template>
      </el-table-column>
      <el-table-column label="优先级" align="center" prop="priority">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.WARRANTY_ORDER_PRIORITY" :value="scope.row.priority" />
        </template>
      </el-table-column>
      <el-table-column label="关联维保协议号" align="center" prop="agreementNo" />
      <el-table-column label="客户名称" align="center" prop="customerName" />
      <el-table-column label="客户联系人" align="center" prop="customerContactName" />
      <el-table-column label="客户联系电话" align="center" prop="customerContactPhone" />
      <el-table-column label="报修地址" align="center" prop="customerAddress" />
      <el-table-column
        label="报修时间"
        align="center"
        prop="reportTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="设备所属单位ID" align="center" prop="owningDeptId" />
      <el-table-column label="服务片区部门ID" align="center" prop="serviceAreaDeptId" />
      <el-table-column label="故障类型ID" align="center" prop="issueTypeId" />
      <el-table-column label="故障类型名称" align="center" prop="issueTypeName" />
      <el-table-column label="预计处理时长(分钟)" align="center" prop="estimatedDuration" width="160px" />
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['warranty:mainten-order:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['warranty:mainten-order:delete']"
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
  <MaintenOrderForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { MaintenOrderApi, MaintenOrderVO } from '@/api/warranty/maintenorder'
import MaintenOrderForm from './MaintenOrderForm.vue'

/** 维修工单 列表 */
defineOptions({ name: 'MaintenOrder' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<MaintenOrderVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  createTime: [],
  workOrderNo: undefined,
  title: undefined,
  orderType: undefined,
  orderStatus: undefined,
  priority: undefined,
  agreementNo: undefined,
  customerName: undefined,
  reportTime: [],
  maintenanceCompanyId: undefined,
  assignedToUserName: undefined,
  owningDeptId: undefined,
  serviceAreaDeptId: undefined,
  issueTypeId: undefined,
  issueTypeName: undefined,
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await MaintenOrderApi.getMaintenOrderPage(queryParams)
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

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await MaintenOrderApi.deleteMaintenOrder(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await MaintenOrderApi.exportMaintenOrder(queryParams)
    download.excel(data, '维修工单.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>