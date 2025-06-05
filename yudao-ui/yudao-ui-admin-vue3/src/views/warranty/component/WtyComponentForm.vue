<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="配件编号" prop="componentCode">
        <el-input v-model="formData.componentCode" placeholder="请输入配件编号" />
      </el-form-item>
      <el-form-item label="配件名称" prop="componentName">
        <el-input v-model="formData.componentName" placeholder="请输入配件名称" />
      </el-form-item>
      <el-form-item label="单位" prop="unit">
        <el-select v-model="formData.unit" placeholder="请选择单位">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.WARRANTY_COMPONENT_UNIT)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入备注"/>
      </el-form-item>
      <el-form-item label="配件维修价格" prop="priceMaintain">
        <el-input v-model="formData.priceMaintain" placeholder="请输入配件维修价格" />
      </el-form-item>
      <el-form-item label="配件更换价格" prop="priceChange">
        <el-input v-model="formData.priceChange" placeholder="请输入配件更换价格" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-radio-group v-model="formData.status">
          <el-radio
            v-for="dict in getBoolDictOptions(DICT_TYPE.WARRANTY_COMPONENT_STATUS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getStrDictOptions, getBoolDictOptions, DICT_TYPE } from '@/utils/dict'
import { WtyComponentApi, WtyComponentVO } from '@/api/warranty/component'

/** 配件 表单 */
defineOptions({ name: 'WtyComponentForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  componentCode: undefined,
  componentName: undefined,
  unit: undefined,
  remark: undefined,
  priceMaintain: undefined,
  priceChange: undefined,
  status: undefined,
})
const formRules = reactive({
  componentCode: [{ required: true, message: '配件编号不能为空', trigger: 'blur' }],
  componentName: [{ required: true, message: '配件名称不能为空', trigger: 'blur' }],
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
      formData.value = await WtyComponentApi.getWtyComponent(id)
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
    const data = formData.value as unknown as WtyComponentVO
    if (formType.value === 'create') {
      await WtyComponentApi.createWtyComponent(data)
      message.success(t('common.createSuccess'))
    } else {
      await WtyComponentApi.updateWtyComponent(data)
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
    componentCode: undefined,
    componentName: undefined,
    unit: undefined,
    remark: undefined,
    priceMaintain: undefined,
    priceChange: undefined,
    status: undefined,
  }
  formRef.value?.resetFields()
}
</script>