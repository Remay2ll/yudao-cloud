<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="维修工单ID" prop="workOrderId">
        <el-input v-model="formData.workOrderId" placeholder="请输入维修工单ID" />
      </el-form-item>
      <el-form-item label="配件ID" prop="partId">
        <el-input v-model="formData.partId" placeholder="请输入配件ID" />
      </el-form-item>
      <el-form-item label="数量" prop="quantity">
        <el-input v-model="formData.quantity" placeholder="请输入数量" />
      </el-form-item>
      <el-form-item label="关于此配件在此工单中的备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入关于此配件在此工单中的备注" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { MaintenOrderPartApi, MaintenOrderPartVO } from '@/api/warranty/maintenorderpart'

/** 工单配件 表单 */
defineOptions({ name: 'MaintenOrderPartForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  workOrderId: undefined,
  partId: undefined,
  quantity: undefined,
  remark: undefined,
})
const formRules = reactive({
  workOrderId: [{ required: true, message: '维修工单ID不能为空', trigger: 'blur' }],
  partId: [{ required: true, message: '配件ID不能为空', trigger: 'blur' }],
  quantity: [{ required: true, message: '数量不能为空', trigger: 'blur' }],
})
const formRef = ref() // 表单 Ref

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
      formData.value = await MaintenOrderPartApi.getMaintenOrderPart(id)
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
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as MaintenOrderPartVO
    if (formType.value === 'create') {
      await MaintenOrderPartApi.createMaintenOrderPart(data)
      message.success(t('common.createSuccess'))
    } else {
      await MaintenOrderPartApi.updateMaintenOrderPart(data)
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
    workOrderId: undefined,
    partId: undefined,
    quantity: undefined,
    remark: undefined,
  }
  formRef.value?.resetFields()
}
</script>