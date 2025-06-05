<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="140px"
      v-loading="formLoading"
    >
      <el-form-item label="工单编号" prop="workOrderNo">
        <el-input v-model="formData.workOrderNo" placeholder="请输入工单编号" />
      </el-form-item>
      <el-form-item label="标题" prop="title">
        <el-input v-model="formData.title" placeholder="请输入工单标题/简述" />
      </el-form-item>
      <el-form-item label="工单类型" prop="orderType">
        <el-select v-model="formData.orderType" placeholder="请选择工单类型">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.WARRANTY_ORDER_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="工单状态" prop="orderStatus">
        <el-select v-model="formData.orderStatus" placeholder="请选择工单状态">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.WARRANTY_ORDER_STATUS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="优先级" prop="priority">
        <el-select v-model="formData.priority" placeholder="请选择优先级">
          <el-option
            v-for="dict in getIntDictOptions(DICT_TYPE.WARRANTY_ORDER_PRIORITY)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="关联维保协议号" prop="agreementNo">
        <el-input v-model="formData.agreementNo" placeholder="请输入关联维保协议号" />
      </el-form-item>
      <el-form-item label="客户ID" prop="customerId">
        <el-input v-model="formData.customerId" placeholder="请输入客户ID" />
      </el-form-item>
      <el-form-item label="客户名称" prop="customerName">
        <el-input v-model="formData.customerName" placeholder="请输入客户名称" />
      </el-form-item>
      <el-form-item label="客户联系人" prop="customerContactName">
        <el-input v-model="formData.customerContactName" placeholder="请输入客户联系人" />
      </el-form-item>
      <el-form-item label="客户联系电话" prop="customerContactPhone">
        <el-input v-model="formData.customerContactPhone" placeholder="请输入客户联系电话" />
      </el-form-item>
      <el-form-item label="报修地址" prop="customerAddress">
        <el-input v-model="formData.customerAddress" placeholder="请输入报修地址" />
      </el-form-item>
      <el-form-item label="问题描述" prop="problemDescription">
        <Editor v-model="formData.problemDescription" height="150px" />
      </el-form-item>
      <el-form-item label="报修时间" prop="reportTime">
        <el-date-picker
          v-model="formData.reportTime"
          type="datetime"
          value-format="x"
          placeholder="选择报修时间"
        />
      </el-form-item>
      <el-form-item label="报修人" prop="reportedByUserId">
        <el-input v-model="formData.reportedByUserId" placeholder="请输入报修人用户ID" />
      </el-form-item>
      <el-form-item label="负责维保公司" prop="maintenanceCompanyId">
        <el-input v-model="formData.maintenanceCompanyId" placeholder="请输入负责维保公司ID" />
      </el-form-item>
      <el-form-item label="维修工" prop="assignedToUserId">
        <el-input v-model="formData.assignedToUserId" placeholder="请输入维修工用户ID" />
      </el-form-item>
      <el-form-item label="维修工姓名" prop="assignedToUserName">
        <el-input v-model="formData.assignedToUserName" placeholder="请输入维修工姓名" />
      </el-form-item>
      <el-form-item label="分派时间" prop="assignmentTime">
        <el-date-picker
          v-model="formData.assignmentTime"
          type="datetime"
          value-format="x"
          placeholder="选择分派时间"
        />
      </el-form-item>
      <el-form-item label="接单时间" prop="acceptanceTime">
        <el-date-picker
          v-model="formData.acceptanceTime"
          type="datetime"
          value-format="x"
          placeholder="选择接单时间"
        />
      </el-form-item>
      <el-form-item label="计划开始时间" prop="scheduledStartTime">
        <el-date-picker
          v-model="formData.scheduledStartTime"
          type="datetime"
          value-format="x"
          placeholder="选择计划开始时间"
        />
      </el-form-item>
      <el-form-item label="计划结束时间" prop="scheduledEndTime">
        <el-date-picker
          v-model="formData.scheduledEndTime"
          type="datetime"
          value-format="x"
          placeholder="选择计划结束时间"
        />
      </el-form-item>
      <el-form-item label="实际开始时间" prop="actualStartTime">
        <el-date-picker
          v-model="formData.actualStartTime"
          type="datetime"
          value-format="x"
          placeholder="选择实际开始时间"
        />
      </el-form-item>
      <el-form-item label="实际完成时间" prop="actualEndTime">
        <el-date-picker
          v-model="formData.actualEndTime"
          type="datetime"
          value-format="x"
          placeholder="选择实际完成时间"
        />
      </el-form-item>
      <el-form-item label="签到地址" prop="checkInLocation">
        <el-input v-model="formData.checkInLocation" placeholder="请输入签到地址" />
      </el-form-item>
      <el-form-item label="签退地址" prop="checkOutLocation">
        <el-input v-model="formData.checkOutLocation" placeholder="请输入签退地址" />
      </el-form-item>
      <el-form-item label="工作总结" prop="workSummary">
        <el-input v-model="formData.workSummary" type="textarea" placeholder="请输入工作总结" />
      </el-form-item>
      <el-form-item label="维保前文件" prop="filesBeforeMaintenance">
        <UploadImg v-model="formData.filesBeforeMaintenance" />
      </el-form-item>
      <el-form-item label="维保后文件" prop="filesAfterMaintenance">
        <UploadImg v-model="formData.filesAfterMaintenance" />
      </el-form-item>
      <el-form-item label="维修员工签字" prop="technicianSignatureFile">
        <UploadImg v-model="formData.technicianSignatureFile" />
      </el-form-item>
      <el-form-item label="客户签字" prop="customerSignatureFile">
        <UploadImg v-model="formData.customerSignatureFile" />
      </el-form-item>
      <el-form-item label="拒绝时间" prop="refuseTime">
        <el-date-picker
          v-model="formData.refuseTime"
          type="datetime"
          value-format="x"
          placeholder="选择拒绝时间"
        />
      </el-form-item>
      <el-form-item label="拒绝原因" prop="refuseReason">
        <el-input v-model="formData.refuseReason" placeholder="请输入拒绝原因" />
      </el-form-item>
      <el-form-item label="取消时间" prop="cancelTime">
        <el-date-picker
          v-model="formData.cancelTime"
          type="datetime"
          value-format="x"
          placeholder="选择取消时间"
        />
      </el-form-item>
      <el-form-item label="取消原因" prop="cancelReason">
        <el-input v-model="formData.cancelReason" placeholder="请输入取消原因" />
      </el-form-item>
      <el-form-item label="取消操作人" prop="cancelledByUserId">
        <el-input v-model="formData.cancelledByUserId" placeholder="请输入取消操作人用户ID" />
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" type="textarea" placeholder="请输入备注" />
      </el-form-item>
      <el-form-item label="所属单位" prop="owningDeptId">
        <el-input v-model="formData.owningDeptId" placeholder="请输入设备所属单位ID" />
      </el-form-item>
      <el-form-item label="服务片区" prop="serviceAreaDeptId">
        <el-input v-model="formData.serviceAreaDeptId" placeholder="请输入服务片区部门ID" />
      </el-form-item>
      <el-form-item label="故障类型ID" prop="issueTypeId">
        <el-input v-model="formData.issueTypeId" placeholder="请输入故障类型ID" />
      </el-form-item>
      <el-form-item label="故障类型名称" prop="issueTypeName">
        <el-input v-model="formData.issueTypeName" placeholder="请输入故障类型名称" />
      </el-form-item>
      <el-form-item label="预计处理时长(分钟)" prop="estimatedDuration" label-width="160px">
        <el-input v-model="formData.estimatedDuration" placeholder="请输入预计处理时长(分钟)" />
      </el-form-item>
    </el-form>
    <!-- 子表的表单 -->
    <el-tabs v-model="subTabsName">
      <el-tab-pane label="维修设备" name="maintenOrderDevice">
        <MaintenOrderDeviceForm ref="maintenOrderDeviceFormRef" :work-order-id="formData.id" />
      </el-tab-pane>
      <el-tab-pane label="配件" name="maintenOrderPart">
        <MaintenOrderPartForm ref="maintenOrderPartFormRef" :work-order-id="formData.id" />
      </el-tab-pane>
    </el-tabs>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getIntDictOptions, DICT_TYPE } from '@/utils/dict'
import { MaintenOrderApi, MaintenOrderVO } from '@/api/warranty/maintenorder'
import MaintenOrderDeviceForm from './components/MaintenOrderDeviceForm.vue'
import MaintenOrderPartForm from './components/MaintenOrderPartForm.vue'

/** 维修工单 表单 */
defineOptions({ name: 'MaintenOrderForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  workOrderNo: undefined,
  title: undefined,
  orderType: undefined,
  orderStatus: undefined,
  priority: undefined,
  agreementNo: undefined,
  customerId: undefined,
  customerName: undefined,
  customerContactName: undefined,
  customerContactPhone: undefined,
  customerAddress: undefined,
  problemDescription: undefined,
  reportTime: undefined,
  reportedByUserId: undefined,
  maintenanceCompanyId: undefined,
  assignedToUserId: undefined,
  assignedToUserName: undefined,
  assignmentTime: undefined,
  acceptanceTime: undefined,
  scheduledStartTime: undefined,
  scheduledEndTime: undefined,
  actualStartTime: undefined,
  actualEndTime: undefined,
  checkInLocation: undefined,
  checkOutLocation: undefined,
  workSummary: undefined,
  filesBeforeMaintenance: undefined,
  filesAfterMaintenance: undefined,
  technicianSignatureFile: undefined,
  customerSignatureFile: undefined,
  refuseTime: undefined,
  refuseReason: undefined,
  cancelTime: undefined,
  cancelReason: undefined,
  cancelledByUserId: undefined,
  remark: undefined,
  owningDeptId: undefined,
  serviceAreaDeptId: undefined,
  issueTypeId: undefined,
  issueTypeName: undefined,
  estimatedDuration: undefined,
})
const formRules = reactive({
  title: [{ required: true, message: '工单标题不能为空', trigger: 'blur' }],
  orderType: [{ required: true, message: '工单类型不能为空', trigger: 'change' }],
  orderStatus: [{ required: true, message: '工单状态不能为空', trigger: 'change' }],
  priority: [{ required: true, message: '优先级不能为空', trigger: 'change' }],
  customerName: [{ required: true, message: '客户名称不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

/** 子表的表单 */
const subTabsName = ref('maintenOrderDevice')
const maintenOrderDeviceFormRef = ref()
const maintenOrderPartFormRef = ref()

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await MaintenOrderApi.getMaintenOrder(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 校验子表单
  try {
    await maintenOrderDeviceFormRef.value.validate()
  } catch (e) {
    subTabsName.value = 'maintenOrderDevice'
    return
  }
  try {
    await maintenOrderPartFormRef.value.validate()
  } catch (e) {
    subTabsName.value = 'maintenOrderPart'
    return
  }
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as MaintenOrderVO
    // 拼接子表的数据
    data.maintenOrderDevices = maintenOrderDeviceFormRef.value.getData()
    data.maintenOrderParts = maintenOrderPartFormRef.value.getData()
    if (formType.value === 'create') {
      await MaintenOrderApi.createMaintenOrder(data)
      message.success(t('common.createSuccess'))
    } else {
      await MaintenOrderApi.updateMaintenOrder(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    workOrderNo: undefined,
    title: undefined,
    orderType: undefined,
    orderStatus: undefined,
    priority: undefined,
    agreementNo: undefined,
    customerId: undefined,
    customerName: undefined,
    customerContactName: undefined,
    customerContactPhone: undefined,
    customerAddress: undefined,
    problemDescription: undefined,
    reportTime: undefined,
    reportedByUserId: undefined,
    maintenanceCompanyId: undefined,
    assignedToUserId: undefined,
    assignedToUserName: undefined,
    assignmentTime: undefined,
    acceptanceTime: undefined,
    scheduledStartTime: undefined,
    scheduledEndTime: undefined,
    actualStartTime: undefined,
    actualEndTime: undefined,
    checkInLocation: undefined,
    checkOutLocation: undefined,
    workSummary: undefined,
    filesBeforeMaintenance: undefined,
    filesAfterMaintenance: undefined,
    technicianSignatureFile: undefined,
    customerSignatureFile: undefined,
    refuseTime: undefined,
    refuseReason: undefined,
    cancelTime: undefined,
    cancelReason: undefined,
    cancelledByUserId: undefined,
    remark: undefined,
    owningDeptId: undefined,
    serviceAreaDeptId: undefined,
    issueTypeId: undefined,
    issueTypeName: undefined,
    estimatedDuration: undefined,
  }
  formRef.value?.resetFields()
}
</script>